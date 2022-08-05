package com.example.cscb07.data.repositories.impl;

import androidx.annotation.NonNull;

import com.example.cscb07.data.models.EventModel;
import com.example.cscb07.data.models.PendingEventModel;
import com.example.cscb07.data.repositories.EventRepository;
import com.example.cscb07.data.results.EventId;
import com.example.cscb07.data.results.VenueId;
import com.example.cscb07.data.util.FirebaseUtil;
import com.example.cscb07.data.util.ServiceLocator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import io.vavr.control.Try;

public class FirebaseEventRepository implements EventRepository {

    @Override
    public void addEvent(String eventName, String venue, String startDate, String endDate, int maxCapacity, String creator, Consumer<Try<EventId>> callback) {
        EventModel event = new EventModel(eventName, venue, startDate, endDate, maxCapacity);
        DatabaseReference pendingEventRef = FirebaseUtil.getPendingEvents();
        pendingEventRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PendingEventModel pending = new PendingEventModel(event, creator);
                String key = pendingEventRef.push().getKey();
                pendingEventRef.child(key).setValue(pending);
//                callback.accept(Try.success(new EventId(key)));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                callback.accept(Try.failure(null));
            }
        });
    }

    @Override
    public void signUpEvent(String user, EventId event, Consumer<Try<?>> callback) {
        DatabaseReference userRef = FirebaseUtil.getUsers().child(user);
        DatabaseReference eventRef = FirebaseUtil.getEvents().child(event.eventId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("events").exists()){
                    GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
                    };
                    List<String> events = snapshot.child("events").getValue(t);
                    events.add(event.eventId);
                    userRef.child("events").setValue(events);
                }
                else{
                    List<String> events = new ArrayList<String>();
                    events.add(event.eventId);
                    userRef.child("events").setValue(events);
                }
                eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int count = snapshot.child("numAttendees").getValue(int.class);
                        int max = snapshot.child("maxCapacity").getValue(int.class);
                        if(count < max) {
                            count += 1;
                            eventRef.child("numAttendees").setValue(count);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void approveEvent(EventId event, Consumer<Try<?>> callback) {
        DatabaseReference pendingEventRef = FirebaseUtil.getPendingEvents().child(event.eventId);
        DatabaseReference eventRef = FirebaseUtil.getEvents();
        pendingEventRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    PendingEventModel pendingEvent = snapshot.getValue(PendingEventModel.class);
                    EventModel e = pendingEvent.event;
                    String key = eventRef.push().getKey();
                    eventRef.child(key).setValue(e);
                    signUpEvent(pendingEvent.creator, new EventId(key), null);
                    pendingEventRef.removeValue();
//                    callback.accept(Try.success(new EventId(key)));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                callback.accept(Try.failure(null));
            }
        });
    }

    @Override
    public void removeEvent(EventId event, Consumer<Try<?>> callback) {
        DatabaseReference pendingEventRef = FirebaseUtil.getPendingEvents().child(event.eventId);
        pendingEventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    snapshot.getRef().removeValue();
                    callback.accept(Try.success("removed"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.accept(Try.success(null));
            }
        });

    }

    @Override
    public void getUpcomingEventsForCurrentUser(EventId startAt, int count, Consumer<Try<List<EventModel>>> callback) {

    }

    @Override
    public void getAllUpcomingEvents(EventId startAt, int count, Consumer<Try<List<EventModel>>> callback) {

    }
}
