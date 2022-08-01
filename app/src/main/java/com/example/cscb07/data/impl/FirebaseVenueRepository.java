package com.example.cscb07.data.impl;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cscb07.data.Court;
import com.example.cscb07.data.Results.VenueResult;
import com.example.cscb07.data.Venue;
import com.example.cscb07.data.VenueRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FirebaseVenueRepository implements VenueRepository {

    DatabaseReference venueRef = FirebaseDatabase.getInstance("https://cscb07-project-f0f07-default-rtdb.firebaseio.com/").getReference("Venues");

    @Override
    public MutableLiveData<VenueResult> addVenue(String name, List<Court> courts) {
        MutableLiveData<VenueResult> data = new MutableLiveData<>();
        if(name==null && courts==null){
            data.setValue(new VenueResult(false));

        }

        venueRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(name!=null) {
                    if (!snapshot.child(name).exists()) {
                        Venue venue = new Venue();
                        venue.setName(name);
                        venue.setCourts(courts);
                        venueRef.child(name).setValue(venue);
                        data.setValue(new VenueResult(true));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                data.setValue(new VenueResult(false));
            }
        });

        return data;

    }

    @Override
    public MutableLiveData<VenueResult> readVenues() {
        MutableLiveData<VenueResult> data = new MutableLiveData<>();
        venueRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child:snapshot.getChildren()) {
                    Venue venue = child.getValue(Venue.class);
                    Log.i("venue", venue.toString());
                }
                data.setValue(new VenueResult(true));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                data.setValue(new VenueResult(false));
                //do something
            }
        });
        return data;
    }
}
