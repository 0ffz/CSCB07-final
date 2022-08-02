package com.example.cscb07.data.impl;

import android.renderscript.Sampler;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.cscb07.data.Court;
import com.example.cscb07.data.Results.VenueResult;
import com.example.cscb07.data.Venue;
import com.example.cscb07.data.VenueRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import java.util.List;


public class FirebaseVenueRepository implements VenueRepository {

    DatabaseReference venueRef = FirebaseDatabase.getInstance("https://cscb07-project-f0f07-default-rtdb.firebaseio.com/").getReference("Venues");

    @Override
    public LiveData<VenueResult> addVenue(String name, List<Court> courts) {
        MutableLiveData<VenueResult> data = new MutableLiveData<>();
        if(name==null && courts==null){
            data.setValue(new VenueResult(false, "", null));
            return data;
        }

        venueRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(name!=null && courts!=null) {
                    if (!snapshot.child(name).exists()) {
                        Venue venue = new Venue();
                        venue.setName(name);
                        venue.setCourts(courts);
                        venueRef.child(name).setValue(courts);
                        data.setValue(new VenueResult(true, name, courts));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return data;
    }

    @Override
    public LiveData<VenueResult> readVenue(String name) {
        MutableLiveData<VenueResult> data = new MutableLiveData<>();
        venueRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(name).exists()) {
                        GenericTypeIndicator<List<Court>> t = new GenericTypeIndicator<List<Court>>() {
                        };
                        List<Court> courts = snapshot.child(name).getValue(t);


                        data.setValue(new VenueResult(true, name, courts ));

                }
                else{
                    data.setValue(new VenueResult(false, "", null));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return data;
    }
}
