package com.example.cscb07.data.repositories.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.cscb07.data.models.VenueModel;
import com.example.cscb07.data.repositories.VenueRepository;
import com.example.cscb07.data.results.VenueId;
import com.example.cscb07.data.results.WithId;
import com.example.cscb07.data.util.FirebaseUtil;
import com.google.firebase.database.*;
import io.vavr.collection.Stream;
import io.vavr.control.Try;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class FirebaseVenueRepository implements VenueRepository {
    @Override
    public void addVenue(String name, String description, Consumer<Try<VenueId>> callback) {
        DatabaseReference venuesRef = FirebaseUtil.getVenues();
        venuesRef.get().addOnSuccessListener(snapshot -> {
            VenueModel venue = new VenueModel(name, description);
            String id = venuesRef.push().getKey();
            venuesRef.child(id).setValue(venue);
            callback.accept(Try.success(new VenueId(id)));
        }).addOnFailureListener(e -> callback.accept(Try.failure(e)));
    }

    @Override
    public void getVenues(Consumer<Try<List<WithId<VenueId, VenueModel>>>> callback) {
        Query query = FirebaseUtil.getVenues()
                .orderByChild("name");
        query.get().addOnSuccessListener(dataSnapshot -> {
            List<WithId<VenueId, VenueModel>> venues = Stream.ofAll(dataSnapshot.getChildren())
                    .map(snapshot -> WithId.of(new VenueId(snapshot.getKey()), snapshot.getValue(VenueModel.class)))
                    .toJavaList();
            callback.accept(Try.success(venues));
        }).addOnFailureListener(e ->
                callback.accept(Try.failure(e)));
    }


}
