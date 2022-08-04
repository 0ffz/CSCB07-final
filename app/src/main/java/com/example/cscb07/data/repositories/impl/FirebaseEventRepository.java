package com.example.cscb07.data.repositories.impl;

import androidx.annotation.NonNull;

import com.example.cscb07.data.models.EventModel;
import com.example.cscb07.data.repositories.EventRepository;
import com.example.cscb07.data.util.ServiceLocator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class FirebaseEventRepository implements EventRepository {

    @Override
    public void addEvent(String name, String venue, long startDate, long endDate, int maxCapacity) {
        EventModel e = new EventModel(name, venue, startDate, endDate, maxCapacity);

        DatabaseReference d = ServiceLocator.getInstance().getDb().getReference();

        //might have to check if the event time doesn't overlap

        DatabaseReference refForKey = d.child("Events").push();
        String key = refForKey.getKey(); //I don't think key will ever be null, this gets a unique key to distinguish the event
        d.child("Events").child(key).setValue(e); //add new event to events
        d.child("Venues").child(venue).child("Events").child(key).setValue(e); //add the event to venue
        d.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events").child(key).setValue(e);//add event to user's events
    }

    @Override
    public void signUpForEvent(String uniqEventKey){

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

    }
}
