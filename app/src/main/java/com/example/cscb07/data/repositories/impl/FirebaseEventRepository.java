package com.example.cscb07.data.repositories.impl;

import androidx.annotation.NonNull;

import com.example.cscb07.data.models.EventModel;
import com.example.cscb07.data.models.PendingEventModel;
import com.example.cscb07.data.repositories.EventRepository;
import com.example.cscb07.data.results.EventId;
import com.example.cscb07.data.results.VenueId;
import com.example.cscb07.data.results.WithId;
import com.example.cscb07.data.util.FirebaseUtil;
import com.example.cscb07.data.util.ServiceLocator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import io.vavr.control.Try;

import java.util.ArrayList;
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
    public void signUpEvent(EventId event, Consumer<Try<?>> callback){

        DatabaseReference d = FirebaseUtil.getDb(); //get database reference
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        d.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful())
                    callback.accept(Try.failure(task.getException()));

                else{
                    DataSnapshot snapshot = task.getResult();
                    DataSnapshot userEvent = snapshot.child("users").child(user).child("events").child(event.eventId);
                    DataSnapshot eventSnapshot = snapshot.child("events");
                    if (!(eventSnapshot.child(event.eventId).exists()))
                        callback.accept(Try.failure(new Exception("event not found")));
                    if(userEvent.exists())
                        callback.accept(Try.failure(new Exception("already signed up for event")));
                    else {
                        EventModel e = eventSnapshot.getValue(EventModel.class);
                        if (e.numAttendees >= e.maxCapacity)
                            callback.accept(Try.failure(new Exception("event is fully booked")));
                        else{
                            int num = e.numAttendees;
                            num += 1;
                            d.child("events").child(event.eventId).child("numAttendees").setValue(num);
                            d.child("users").child(user).child("events").child(event.eventId).setValue(e.getStartDate()); //keys map to start date
                            callback.accept(Try.success(e));
                        }
                    }
                }
            }
        });

    }

    @Override
    public void approveEvent(EventId event, Consumer<Try<?>> callback) {
        DatabaseReference d = FirebaseUtil.getDb();
        DatabaseReference pending = FirebaseUtil.getPendingEvents().child(event.eventId);

        pending.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    DataSnapshot snapshot = task.getResult();// get snapshot of the specific event in pendingEvents
                    PendingEventModel p = snapshot.getValue(PendingEventModel.class);
                    EventModel e = p.event; // make an object of the event
                    e.numAttendees = 1; // remove from pending means there should only be 1 person attending (the User)
                    d.child("events").child(event.eventId).setValue(e); // make the event in Events
                    d.child("users").child(p.creator).child("events").child(event.eventId).setValue(e.startDateMillis); // make the event under the currentUser
                    d.child("venues").child(e.venue).child("events").child(event.eventId).setValue(e.startDateMillis); //add event to under the venue it is in
                    removeEvent(event, callback);

                }
                else{
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
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseUtil.getUsers().child(userID).child("events"); //get the events
        ref.orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> eventIds = new ArrayList<>();
                for(DataSnapshot event: snapshot.getChildren()){
                    eventIds.add(event.getKey());
                }
                     FirebaseUtil.getEvents().get().addOnSuccessListener(dataSnapshot->{
                        List<WithId<EventId, EventModel>> events = new ArrayList<>();
                        for(String e: eventIds){
                            EventModel event = dataSnapshot.child(e).getValue(EventModel.class);
                            events.add(WithId.of(new EventId(e), event));
                        }

                        callback.accept(Try.success(events));
                    });

           }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.accept(Try.failure(error.toException()));
            }

        });

    }

    @Override
    public void getAllUpcomingEvents(Consumer<Try<List<WithId<EventId, EventModel>>>> callback) {
        DatabaseReference d = ServiceLocator.getInstance().getDb().getReference();
        d.child("events").orderByChild("startTime").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    List<WithId<EventId, EventModel>> events = new ArrayList<>();
                    DataSnapshot snap = task.getResult();
                    for (DataSnapshot s : snap.getChildren()){
                        EventModel e = s.getValue(EventModel.class);
                        events.add(WithId.of(new EventId(s.getKey()), e));
                    }

                    callback.accept(Try.success(events));
                }
                else
                    callback.accept(Try.failure(task.getException()));
            }
        });
    }

    @Override
    public void getEventsForVenue(VenueId venue, Consumer<Try<List<WithId<EventId, EventModel>>>> callback) {
        DatabaseReference ref = FirebaseUtil.getVenues().child(venue.venueId).child("events"); //get the events
        ref.orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> eventIds = new ArrayList<>();
                for(DataSnapshot event: snapshot.getChildren()){
                    eventIds.add(event.getKey());
                }
                FirebaseUtil.getEvents().get().addOnSuccessListener(dataSnapshot->{
                    List<WithId<EventId, EventModel>> events = new ArrayList<>();
                    for(String e: eventIds){
                        EventModel event = dataSnapshot.child(e).getValue(EventModel.class);
                        events.add(WithId.of(new EventId(e), event));
                    }

                    callback.accept(Try.success(events));
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.accept(Try.failure(error.toException()));
            }

        });
    }
}
