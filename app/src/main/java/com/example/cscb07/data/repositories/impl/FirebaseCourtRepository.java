package com.example.cscb07.data.repositories.impl;

import androidx.annotation.NonNull;

import com.example.cscb07.data.RepositoryCallback;
import com.example.cscb07.data.Results.CourtResult;
import com.example.cscb07.data.models.CourtModel;
import com.example.cscb07.data.repositories.CourtRepository;
import com.example.cscb07.data.util.FirebaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FirebaseCourtRepository implements CourtRepository {

    @Override
    public void addCourt(@NonNull String name, List<String> sports, RepositoryCallback<CourtResult> callback) {
        DatabaseReference courtRef = FirebaseUtil.getCourts().child(name);
        courtRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    CourtModel court = new CourtModel();
                    court.setSports(sports);
                    court.setUpcomingEvents(null);
                    courtRef.setValue(court);
                    callback.onComplete(new CourtResult(true, name));
                }
                else
                    callback.onComplete(new CourtResult(false, null));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onComplete(new CourtResult(false, null));
            }
        });
    }
}
