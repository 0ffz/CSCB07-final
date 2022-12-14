package com.example.cscb07.data.repositories.impl;

import com.example.cscb07.R;
import com.example.cscb07.data.models.EventModel;
import com.example.cscb07.data.repositories.EventRepository;
import com.example.cscb07.data.results.EventId;
import com.example.cscb07.data.results.VenueId;
import com.example.cscb07.data.results.WithId;
import com.example.cscb07.data.util.FirebaseUtil;
import com.example.cscb07.data.util.Message;
import com.example.cscb07.ui.state.EventUiState;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import io.vavr.control.Try;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.example.cscb07.data.repositories.impl.QueryUtil.getEvents;
import static com.example.cscb07.data.repositories.impl.QueryUtil.getEventsForKeys;

public class FirebaseEventRepository implements EventRepository {

    @Override
    public void addEvent(VenueId venue, String eventName, String description, long startDate, long endDate, int maxCapacity, Consumer<Try<EventId>> callback) {
        EventModel e = new EventModel(eventName, venue.venueId, description, startDate, endDate, maxCapacity); //make event
        String creator = FirebaseAuth.getInstance().getCurrentUser().getUid(); //get current user

        String pendingEventKey = FirebaseUtil.getPendingEvents().push().getKey(); // store key in variable
        FirebaseUtil.getVenue(venue).child("pendingEvents").child(pendingEventKey).setValue(startDate);
        FirebaseUtil.getPendingEvents().child(pendingEventKey).setValue(e); // store the event under pendingEvents
        FirebaseUtil.getPendingCreators().child(pendingEventKey).setValue(creator);
        callback.accept(Try.success(new EventId(pendingEventKey)));
    }

    @Override
    public void signUpEvent(EventId event, Consumer<Try<?>> callback) {
        DatabaseReference eventRef = FirebaseUtil.getEvent(event);
        eventRef.get().addOnSuccessListener(snapshot -> {
            EventModel e = snapshot.getValue(EventModel.class);
            if (e == null)
                callback.accept(Try.failure(new Message(R.string.error_event_not_found)));
            else if (e.numAttendees >= e.maxCapacity)
                callback.accept(Try.failure(new Message(R.string.error_event_full)));
            else {
                eventRef.child("numAttendees").setValue(e.numAttendees + 1);
                FirebaseUtil.getCurrentUserRef().child("events")
                        .child(event.key).setValue(e.startDateMillis);
                callback.accept(Try.success(null));
            }
        }).addOnFailureListener(e -> callback.accept(Try.failure(e)));
    }

    @Override
    public void approveEvent(EventId eventId, Consumer<Try<?>> callback) {
        FirebaseUtil.getPendingEvent(eventId).get().addOnSuccessListener(snapshot -> {
            EventModel event = snapshot.getValue(EventModel.class);
            event.numAttendees = 1; // remove from pending means there should only be 1 person attending (the User)

            // Move to events
            FirebaseUtil.getEvent(eventId).setValue(event);

            // add event to the venue it is in
            FirebaseUtil.getVenue(new VenueId(event.venue)).child("events").child(eventId.key).setValue(event.startDateMillis);

            // Add book creator for this event
            DatabaseReference creatorRef = FirebaseUtil.getPendingCreators().child(eventId.key);
            creatorRef.get().addOnSuccessListener(creatorSnapshot -> {
                String creator = creatorSnapshot.getValue(String.class);
                FirebaseUtil.getUsers().child(creator).child("events").child(eventId.key).setValue(event.startDateMillis);
                removePendingEvent(eventId);
                callback.accept(Try.success(null));
            }).addOnFailureListener(e -> callback.accept(Try.failure(e)));
        }).addOnFailureListener(e -> callback.accept(Try.failure(e)));
    }

    @Override
    public void removePendingEvent(EventId event) {
        FirebaseUtil.getPendingEvent(event).removeValue();
        FirebaseUtil.getPendingCreators().child(event.key).removeValue();
    }


    @Override
    public void getUpcomingEventsForCurrentUser(Consumer<Try<List<WithId<EventId, EventModel>>>> callback) {
        Query q = FirebaseUtil.getCurrentUserRef().child("events").orderByValue().startAfter(new Date().getTime());
        getEventsForKeys(q, FirebaseUtil.getEvents(), callback);
    }

    @Override
    public void getAllUpcomingEvents(Consumer<Try<List<WithId<EventId, EventModel>>>> callback) {
        Query q = FirebaseUtil.getEvents().orderByChild("startDateMillis").startAt(new Date().getTime());
        getEvents(q, callback);
    }

    @Override
    public void getEventsForVenue(VenueId venue, Consumer<Try<List<WithId<EventId, EventModel>>>> callback) {
        Query eventKeyList = FirebaseUtil.getVenue(venue).child("events").orderByValue().startAfter(new Date().getTime());
        getEventsForKeys(eventKeyList, FirebaseUtil.getEvents(), callback);
    }

    public void getAllPendingEvents(Consumer<Try<List<WithId<EventId, EventModel>>>> callback) {
        Query q = FirebaseUtil.getPendingEvents().orderByChild("startDateMillis").startAt(new Date().getTime());
        getEvents(q, callback);
    }

    @Override
    public void getPendingEventsForVenue(VenueId venue, Consumer<Try<List<WithId<EventId, EventModel>>>> callback) {
        Query pendingEventKeyList = FirebaseUtil.getVenue(venue).child("pendingEvents").orderByValue().startAfter(new Date().getTime());
        getEventsForKeys(pendingEventKeyList, FirebaseUtil.getPendingEvents(), callback);
    }

    @Override
    public void mapToEventUiState(List<WithId<EventId, EventModel>> events, Consumer<Try<List<EventUiState>>> callback) {
        FirebaseUtil.getVenues().get().addOnSuccessListener(venuesSnapshot ->
                getJoinedEvents(joinedResult -> joinedResult.onSuccess(joined ->
                                callback.accept(Try.success(events.stream().map(it -> new EventUiState(
                                        it.model.name,
                                        it.model.description,
                                        it.model.getStartDate(),
                                        it.model.getEndDate(),
                                        it.model.numAttendees,
                                        it.model.maxCapacity,
                                        it.id,
                                        new VenueId(it.model.venue),
                                        venuesSnapshot.child(it.model.venue).child("name").getValue(String.class),
                                        joined.contains(it.id)
                                )).collect(Collectors.toList())))
                        ).onFailure(e -> callback.accept(Try.failure(e)))
                )
        ).addOnFailureListener(e -> callback.accept(Try.failure(e)));
    }

    private void getJoinedEvents(Consumer<Try<Set<EventId>>> callback) {
        QueryUtil.readEventKeys(FirebaseUtil.getCurrentUserRef().child("events"), (snapshot, result) ->
                result.onSuccess(eventIds -> callback.accept(Try.success(eventIds.toJavaSet())))
                        .onFailure(e -> callback.accept(Try.failure(e)))
        );
    }

}
