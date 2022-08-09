package com.example.cscb07.data.repositories.impl;

import androidx.annotation.NonNull;
import com.example.cscb07.data.models.EventModel;
import com.example.cscb07.data.models.PendingEventModel;
import com.example.cscb07.data.repositories.EventRepository;
import com.example.cscb07.data.results.EventId;
import com.example.cscb07.data.results.VenueId;
import com.example.cscb07.data.results.WithId;
import com.example.cscb07.data.util.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import io.vavr.collection.Stream;
import io.vavr.control.Try;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class FirebaseEventRepository implements EventRepository {

    @Override
    public void addEvent(VenueId venue, String eventName, String description, long startDate, long endDate, int maxCapacity, Consumer<Try<EventId>> callback) {
        EventModel e = new EventModel(eventName, venue.venueId, description, startDate, endDate, maxCapacity); //make event
        String creator = FirebaseAuth.getInstance().getCurrentUser().getUid(); //get current user
        DatabaseReference d = FirebaseUtil.getPendingEvents(); //get database reference
        PendingEventModel p = new PendingEventModel(e, creator); //new pending event;
        String key = d.push().getKey(); // store key in variable
        d.child(key).setValue(p); // store the event under pendingEvents
        callback.accept(Try.success(new EventId(key)));

    }

    @Override
    public void signUpEvent(EventId event, Consumer<Try<?>> callback) {
        DatabaseReference eventRef = FirebaseUtil.getEvent(event);
        eventRef.get().addOnSuccessListener(snapshot -> {
            EventModel e = snapshot.getValue(EventModel.class);
            if (e == null)
                callback.accept(Try.failure(new Exception("event not found")));
            else if (e.numAttendees >= e.maxCapacity)
                callback.accept(Try.failure(new Exception("event is fully booked")));
            else {
                eventRef.child("numAttendees").setValue(e.numAttendees + 1);
                FirebaseUtil.getCurrentUserRef().child("events").child(event.eventId).setValue(true);
                callback.accept(Try.success(null));
            }
        }).addOnFailureListener(e -> callback.accept(Try.failure(e)));
    }

    @Override
    public void approveEvent(EventId event, Consumer<Try<?>> callback) {
        DatabaseReference d = FirebaseUtil.getDb();
        DatabaseReference pending = FirebaseUtil.getPendingEvents().child(event.eventId);

        pending.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();// get snapshot of the specific event in pendingEvents
                    PendingEventModel p = snapshot.getValue(PendingEventModel.class);
                    EventModel e = p.event; // make an object of the event
                    e.numAttendees = 1; // remove from pending means there should only be 1 person attending (the User)
                    d.child("events").child(event.eventId).setValue(e); // make the event in Events
                    d.child("users").child(p.creator).child("events").child(event.eventId).setValue(e.startDateMillis); // make the event under the currentUser
                    d.child("venues").child(e.venue).child("events").child(event.eventId).setValue(e.startDateMillis); //add event to under the venue it is in
                    removeEvent(event, callback);

                } else {
                    callback.accept(Try.failure(task.getException()));
                }
            }
        });
    }

    @Override
    public void removeEvent(EventId event, Consumer<Try<?>> callback) {
        DatabaseReference d = FirebaseUtil.getPendingEvents().child(event.eventId);
        d.removeValue(); // remove event from pendingEvents, don't need to remove anywhere else
        callback.accept(Try.success("removed from pending"));
    }


    @Override
    public void getUpcomingEventsForCurrentUser(Consumer<Try<List<WithId<EventId, EventModel>>>> callback) {
        Query q = FirebaseUtil.getCurrentUserRef().child("events");
        q.get().addOnSuccessListener(dataSnapshot -> {
            Stream<String> userEvents = Stream.ofAll(dataSnapshot.getChildren()).map(DataSnapshot::getKey);

            Query q2 = FirebaseUtil.getEvents().orderByChild("endDateMillis").startAt(new Date().getTime());
            q2.get().addOnSuccessListener(dataSnapshot1 -> {
                List<WithId<EventId, EventModel>> events = userEvents
                        .map(key ->  WithId.of(new EventId(key), dataSnapshot1.child(key).getValue(EventModel.class)))
                        .filter(withId -> withId.model != null)
                        .toJavaList();
                callback.accept(Try.success(events));
            }).addOnFailureListener(e -> callback.accept(Try.failure(e)));

        }).addOnFailureListener(e -> callback.accept(Try.failure(e)));
    }

    @Override
    public void getAllUpcomingEvents(Consumer<Try<List<WithId<EventId, EventModel>>>> callback) {
        Query q = FirebaseUtil.getEvents().orderByChild("endDateMillis").startAt(new Date().getTime());
        q.get().addOnSuccessListener(dataSnapshot -> {
            List<WithId<EventId, EventModel>> events = Stream.ofAll(dataSnapshot.getChildren())
                    .map(snapshot -> WithId.of(new EventId(snapshot.getKey()), snapshot.getValue(EventModel.class)))
                    .toJavaList();
            callback.accept(Try.success(events));
        }).addOnFailureListener(e ->
                callback.accept(Try.failure(e)));
    }

    @Override
    public void getEventsForVenue(VenueId venue, Consumer<Try<List<WithId<EventId, EventModel>>>> callback) {
        Query q = FirebaseUtil.getVenues().child(venue.venueId).child("events").orderByValue().startAt(new Date().getTime()); //get the events
        q.get().addOnSuccessListener(dataSnapshot -> {
            Stream<String> venueEvents = Stream.ofAll(dataSnapshot.getChildren()).map(DataSnapshot::getKey);
            Query q2 = FirebaseUtil.getEvents();
            q2.get().addOnSuccessListener(dataSnapshot1 -> {
                List<WithId<EventId, EventModel>> events = venueEvents
                        .map(event -> WithId.of(new EventId(event), dataSnapshot1.child(event).getValue(EventModel.class)))
                        .toJavaList();
                callback.accept(Try.success(events));

            }).addOnFailureListener(e ->
                    callback.accept(Try.failure(e)));

        });
    }

    public void getAllPendingEvents(Consumer<Try<List<WithId<EventId, EventModel>>>> callback) {
        Query query = FirebaseUtil.getPendingEvents();
        query.get().addOnSuccessListener(dataSnapshot -> {
            List<WithId<EventId, EventModel>> events = Stream.ofAll(dataSnapshot.getChildren())
                    .map(snapshot -> WithId.of(new EventId(snapshot.getKey()), snapshot.child("event").getValue(EventModel.class)))
                    .toJavaList();
            callback.accept(Try.success(events));
        }).addOnFailureListener(e ->
                callback.accept(Try.failure(e)));
    }
}
