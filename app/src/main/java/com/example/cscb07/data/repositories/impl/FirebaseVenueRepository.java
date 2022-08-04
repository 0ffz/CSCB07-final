package com.example.cscb07.data.repositories.impl;

import androidx.annotation.NonNull;
import com.example.cscb07.data.RepositoryCallback;
import com.example.cscb07.data.models.VenueModel;
import com.example.cscb07.data.repositories.VenueRepository;
import com.example.cscb07.data.results.VenueResult;
import com.example.cscb07.data.util.FirebaseUtil;
import com.example.cscb07.ui.state.VenueUiState;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;


public class FirebaseVenueRepository implements VenueRepository {
    @Override
    public void addVenue(@NotNull String name) {
        DatabaseReference venueRef = FirebaseUtil.getVenues().child(name);
        venueRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    VenueModel venue = new VenueModel(name, Collections.emptyList());
                    venueRef.setValue(venue);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void readVenue(@NotNull String name, @NotNull RepositoryCallback<VenueResult> callback) {
        FirebaseUtil.getVenues().child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                VenueModel venue = snapshot.getValue(VenueModel.class);
                if (venue != null) {
                    callback.onComplete(new VenueResult(true, name, venue.courts));
                } else {
                    callback.onComplete(new VenueResult(false, "", null));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void getVenues(int amount, int page, RepositoryCallback<List<VenueUiState>> callback) {
        Query query = FirebaseUtil.getVenues().orderByKey().startAt(page * amount).limitToFirst(amount);
        query.get().addOnSuccessListener(dataSnapshot -> {

            for (DataSnapshot venue : dataSnapshot.getChildren()) {
                venue.getValue(VenueModel.class);
            }
            //TODO return the list
//            callback.onComplete();
        });
    }
}
