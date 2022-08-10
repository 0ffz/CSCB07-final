package com.example.cscb07.data.util;

import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface TryValueListener extends ValueEventListener {
    @Override
    void onDataChange(@NonNull @NotNull DataSnapshot snapshot);

    @Override
    default void onCancelled(@NonNull @NotNull DatabaseError error) {

    }
}
