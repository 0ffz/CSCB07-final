package com.example.cscb07.data.repositories.impl;

import androidx.annotation.NonNull;

import com.example.cscb07.data.models.EventModel;
import com.example.cscb07.data.repositories.EventRepository;
import com.example.cscb07.data.results.EventId;
import com.example.cscb07.data.results.VenueId;
import com.example.cscb07.data.util.ServiceLocator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.Date;
import java.util.function.Consumer;

public class FirebaseEventRepository implements EventRepository {

    @Override
    public void addEvent(VenueId venue, String eventName, String description, long startDate, long endDate, int maxCapacity, Consumer<Try<EventId>> callback) {
       EventModel e = new EventModel(eventName, venue.venueId, startDate, endDate, maxCapacity); //make event
       DatabaseReference d = ServiceLocator.getInstance().getDb().getReference(); //get database reference
       DatabaseReference refForKey = d.child("Events").push(); // get key for the event
       String key = refForKey.getKey(); // store key in variable
       d.child("pendingEvents").child(key).child("EventModel").setValue(e); // store the event under pendingEvents
       d.child("pendingEvents").child(key).child("UserID").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid()); // add the UserID to the event under pendingEvents

        /*EventModel e = new EventModel(name, venue, startDate, endDate, maxCapacity);

        DatabaseReference d = ServiceLocator.getInstance().getDb().getReference();

        //might have to check if the event time doesn't overlap

        DatabaseReference refForKey = d.child("Events").push();
        String key = refForKey.getKey(); //I don't think key will ever be null, this gets a unique key to distinguish the event
        d.child("Events").child(key).setValue(e); //add new event to events
        d.child("Venues").child(venue).child("Events").child(key).setValue(e); //add the event to venue
        d.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events").child(key).setValue(e);//add event to user's events

         */
    }

    @Override
    public void signUpEvent(EventId event, Consumer<Try<?>> callback){

        DatabaseReference d = ServiceLocator.getInstance().getDb().getReference(); //get database reference
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        d.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful())
                {
                    System.out.println("Not Successful"); // replace for error message
                }
                else{
                    DataSnapshot snapshot = task.getResult();
                    if (snapshot.child("users").child(userID).child("events").child(event.eventId).exists()){
                        System.out.println("Don't add event"); // replace for error message, event already exists
                    }
                    else {
                        EventModel e = snapshot.child("events").child(event.eventId).getValue(EventModel.class);
                        if (e.numAttendees >= e.maxCapacity)
                            System.out.println("Too many people"); // replace for error message, event is fully booked
                        else{
                            System.out.println("adding event to user"); // remove
                            int num = snapshot.child("events").child(event.eventId).child("numAttendees").getValue(int.class);
                            num += 1;
                            d.child("events").child(event.eventId).child("numAttendees").setValue(num);
                            e.numAttendees += 1;
                            d.child("users").child(userID).child("events").child(event.eventId).setValue(true);

                        }
                    }
                }

            }
        });
        /*
        DatabaseReference ref = ServiceLocator.getInstance().getDb().getReference();
        String childID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ref.get().addOnCompleteListener(task -> {
            DataSnapshot snapshot = task.getResult();
            if (snapshot.child("Users").child(childID).child("Events").child(uniqEventKey).exists()){
                System.out.println("Don't add event");
            }
            else {
                EventModel e = snapshot.child("Events").child(uniqEventKey).getValue(EventModel.class);
                if (e.numAttendees >= e.maxCapacity)
                    System.out.println("Too many people");
                else{
                    System.out.println("adding event to user");
                    int num = snapshot.child("Events").child(uniqEventKey).child("currentNum").getValue(int.class);
                    num += 1;
                    ref.child("Events").child(uniqEventKey).child("currentNum").setValue(num);
                    e.numAttendees += 1;
                    ref.child("Users").child(childID).child("Events").child(uniqEventKey).setValue(true); // don't need this, can just store all IDs

                }
            }
        });

         */

    }

    @Override
    public void approveEvent(EventId event, Consumer<Try<?>> callback) {
        DatabaseReference d = ServiceLocator.getInstance().getDb().getReference();

        d.child("pendingEvents").child(event.eventId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    DataSnapshot snapshot = task.getResult(); // get snapshot of the specific event in pendingEvents

                    EventModel e = snapshot.child("eventModel").getValue(EventModel.class); // make an object of the event
                    e.numAttendees = 1; // remove from pending means there should only be 1 person attending (the User)
                    String userID = snapshot.child("userID").getValue(String.class); // get the userID from database
                    d.child("events").child(event.eventId).setValue(e); // make the event in Events
                    d.child("users").child(userID).child("events").child(event.eventId).setValue(true); // make the event under the currentUser

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
        DatabaseReference d = ServiceLocator.getInstance().getDb().getReference();
        d.child("pendingEvents").child(event.eventId).removeValue(); // remove event from pendingEvents, don't need to remove anywhere else
    }

    @Override
    public void getUpcomingEventsForCurrentUser(EventId startAt, int count, Consumer<Try<List<EventModel>>> callback) {

    }

    @Override
    public void getAllUpcomingEvents(EventId startAt, int count, Consumer<Try<List<EventModel>>> callback) {

    }
}
