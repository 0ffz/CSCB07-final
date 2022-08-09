package com.example.cscb07.data.repositories.impl;

import com.example.cscb07.data.models.UserModel;
import com.example.cscb07.data.repositories.UserRepository;
import com.example.cscb07.data.results.EventId;
import com.example.cscb07.data.util.FirebaseUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.GenericTypeIndicator;
import io.vavr.control.Try;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FirebaseUserRepository implements UserRepository {
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    public void registerUser(@NotNull String email, @NotNull String password, Consumer<Try<FirebaseUser>> callback) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                UserModel user = new UserModel(email, false);
                FirebaseUtil.getUsers().child(auth.getCurrentUser().getUid()).setValue(user);
                callback.accept(Try.success(auth.getCurrentUser()));
            } else {
                callback.accept(Try.failure(task.getException()));
            }
        });
    }

    public void signIn(@NotNull String email, @NotNull String password, Consumer<Try<FirebaseUser>> callback) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.accept(Try.success(auth.getCurrentUser()));
            } else {
                callback.accept(Try.failure(task.getException()));
            }
        });
    }

    @Override
    public void signOutCurrentUser() {
        auth.signOut();
    }

    @Override
    public void checkIfAdmin() {
        // TO-DO
    }

    @Override
    public void getJoinedEvents(Consumer<Try<Set<EventId>>> callback) {
        GenericTypeIndicator<Map<String, Object>> t = new GenericTypeIndicator<Map<String, Object>>() {
        };
        FirebaseUtil.getCurrentUserRef().child("events").get()
                .addOnSuccessListener(dataSnapshot -> {
                    Set<String> eventIds = Optional.ofNullable(dataSnapshot.getValue(t))
                            .map(Map::keySet)
                            .orElse(Collections.emptySet());
                    callback.accept(Try.success(eventIds.stream().map(EventId::new).collect(Collectors.toSet())));
                })
                .addOnFailureListener(e -> callback.accept(Try.failure(e)));
    }
}
