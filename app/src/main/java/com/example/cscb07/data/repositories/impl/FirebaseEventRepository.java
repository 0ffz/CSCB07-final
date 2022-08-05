package com.example.cscb07.data.repositories.impl;

import android.media.metrics.Event;

import androidx.annotation.NonNull;

import com.example.cscb07.data.models.EventModel;
import com.example.cscb07.data.models.PendingEventModel;
import com.example.cscb07.data.models.VenueModel;
import com.example.cscb07.data.repositories.EventRepository;
import com.example.cscb07.data.results.EventId;
import com.example.cscb07.data.results.VenueId;
import com.example.cscb07.data.results.WithId;
import com.example.cscb07.data.util.FirebaseUtil;
import com.example.cscb07.data.util.ServiceLocator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import io.vavr.collection.Stream;
import io.vavr.control.Try;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class FirebaseEventRepository implements EventRepository {

    @Override
    public void addEvent(VenueId venue, String eventName, String description, long startDate, long endDate, int maxCapacity, Consumer<Try<EventId>> callback) {
       EventModel e = new EventModel(eventName, venue.venueId, startDate, endDate, maxCapacity); //make event
        String creator = FirebaseAuth.getInstance().getCurrentUser().getUid(); //get current user
       DatabaseReference d = FirebaseUtil.getPendingEvents(); //get database reference
        PendingEventModel p = new PendingEventModel(e, creator); //new pending event;
        String key = d.push().getKey(); // store key in variable
       d.child(key).setValue(p); // store the event under pendingEvents

    }

    @Override
    public void signUpEvent(EventId event, Consumer<Try<?>> callback){

        DatabaseReference d = FirebaseUtil.getDb(); //get database reference
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        d.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful())
                {
                    callback.accept(Try.failure(null));
                }
                else{
                    DataSnapshot snapshot = task.getResult();
                    if (snapshot.child("events").child(event.eventId).exists()){
                        System.out.println("Don't add event"); // replace for error message, event already exists
                    }
                    else {
                        EventModel e = snapshot.child("events").child(event.eventId).getValue(EventModel.class);
                        if (e.numAttendees >= e.maxCapacity)
                            System.out.println("Too many people"); // replace for error message, event is fully booked
                        else{
                            System.out.println("adding event to user"); // remove
                            int num = e.numAttendees;
                            num += 1;
                            d.child("events").child(event.eventId).child("numAttendees").setValue(num);
                            d.child("users").child(user).child("events").child(event.eventId).setValue(e.getStartDate().getTime()); //keys map to start date

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
                    d.child("users").child(p.creator).child("events").child(event.eventId).setValue(e.getStartDate().getTime()); // make the event under the currentUser
                    removeEvent(event, callback);

                }
                else{
                    //error
                    System.out.println("Error");
                }
            }
        });
    }

    @Override
    public void removeEvent(EventId event, Consumer<Try<?>> callback) {
        DatabaseReference d = FirebaseUtil.getPendingEvents().child(event.eventId);
        d.removeValue(); // remove event from pendingEvents, don't need to remove anywhere else
    }


    @Override
    public void getUpcomingEventsForCurrentUser(EventId startAt, int count, Consumer<Try<List<EventModel>>> callback) {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseUtil.getUsers().child(userID).child("events");
        ref.orderByValue().get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    List<String> eventIds = new ArrayList<String>();
                    for(DataSnapshot event: task.getResult().getChildren()){
                        eventIds.add(event.getKey());
                    }
                    Query q = FirebaseUtil.getEvents();
                    q.get().addOnSuccessListener(dataSnapshot->{
                        List<EventModel> events = new ArrayList<EventModel>();
                        for(String e: eventIds){
                            EventModel event = dataSnapshot.child(e).getValue(EventModel.class);
                            events.add(event);
                        }
                        callback.accept(Try.success(events));
                    });

                }
                else
                    callback.accept(Try.failure(null));
            }
        });

    }

    @Override
    public void getAllUpcomingEvents(EventId startAt, int count, Consumer<Try<List<EventModel>>> callback) {
        DatabaseReference d = ServiceLocator.getInstance().getDb().getReference();
        d.child("events").orderByChild("startTime").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    List<EventModel> events = new ArrayList<EventModel>();
                    DataSnapshot snap = task.getResult();
                    for (DataSnapshot s : snap.getChildren()){
                        EventModel e = s.getValue(EventModel.class);
                        events.add(e);
                    }

                    callback.accept(Try.success(events));
                }
                else
                    callback.accept(Try.failure(task.getException()));
            }
        });
    }
}
