package com.example.cscb07.data.repositories.impl;

import com.example.cscb07.data.models.VenueModel;
import com.example.cscb07.data.repositories.VenueRepository;
import com.example.cscb07.data.results.VenueId;
import com.example.cscb07.data.results.WithId;
import com.example.cscb07.data.util.FirebaseUtil;
import com.example.cscb07.data.util.TryValueListener;
import com.example.cscb07.ui.state.VenueUiState;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import io.vavr.collection.Stream;
import io.vavr.control.Try;

import java.util.List;
import java.util.function.Consumer;


public class FirebaseVenueRepository implements VenueRepository {
    @Override
    public void addVenue(String name, String description, Consumer<Try<VenueUiState>> callback) {
        DatabaseReference venuesRef = FirebaseUtil.getVenues();
        venuesRef.get().addOnSuccessListener(snapshot -> {
            VenueModel venue = new VenueModel(name, description);
            String id = venuesRef.push().getKey();
            venuesRef.child(id).setValue(venue);
            callback.accept(Try.success(new VenueUiState(name, description, new VenueId(id))));
        }).addOnFailureListener(e -> callback.accept(Try.failure(e)));
    }

    @Override
    public void getVenues(Consumer<Try<List<WithId<VenueId, VenueModel>>>> callback) {
        Query query = FirebaseUtil.getVenues()
                .orderByChild("name");
        query.get().addOnSuccessListener(dataSnapshot -> {
            List<WithId<VenueId, VenueModel>> venues = Stream.ofAll(dataSnapshot.getChildren())
                    .map(child -> WithId.of(new VenueId(child.getKey()), child.getValue(VenueModel.class)))
                    .toJavaList();
            callback.accept(Try.success(venues));
        }).addOnFailureListener(e ->
                callback.accept(Try.failure(e)));
    }

    public void getVenueName(VenueId venue, Consumer<Try<String>> callback) {
        Query query = FirebaseUtil.getVenue(venue).child("name");
        query.addListenerForSingleValueEvent((TryValueListener) snapshot -> {
            callback.accept(Try.success(snapshot.getValue(String.class)));
        });
//        .addOnFailureListener(e -> callback.accept(Try.failure(e)));
    }


}
