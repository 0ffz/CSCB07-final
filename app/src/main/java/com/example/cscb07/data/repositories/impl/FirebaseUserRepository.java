package com.example.cscb07.data.repositories.impl;

import com.example.cscb07.data.models.UserModel;
import com.example.cscb07.data.repositories.UserRepository;
import com.example.cscb07.data.results.EventId;
import com.example.cscb07.data.util.FirebaseUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import io.vavr.control.Try;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.function.Consumer;

public class FirebaseUserRepository implements UserRepository {
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    public void registerUser(@NotNull String email, @NotNull String password, Consumer<Try<FirebaseUser>> callback) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(result -> {
            UserModel user = new UserModel(email, false);
            FirebaseUtil.getCurrentUserRef().setValue(user);
            callback.accept(Try.success(auth.getCurrentUser()));
        }).addOnFailureListener(e -> callback.accept(Try.failure(e)));
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
    public void checkIfAdmin(Consumer<Boolean> callback) {
        FirebaseUtil.getCurrentUserRef().child("admin").get().addOnCompleteListener(task ->
                callback.accept(Boolean.TRUE.equals(task.getResult().getValue(boolean.class)))
        );
    }

    @Override
    public void getJoinedEvents(Consumer<Try<Set<EventId>>> callback) {
        QueryUtil.readEventKeys(FirebaseUtil.getCurrentUserRef().child("events"), (snapshot, result) ->
                result.onSuccess(eventIds -> callback.accept(Try.success(eventIds.toJavaSet())))
                        .onFailure(e -> callback.accept(Try.failure(e)))
        );
    }

    private void isUserInEvent(EventId event, Consumer<Boolean> callback) {
        FirebaseUtil.getCurrentUserRef().child("events").child(event.key)
                .get().addOnCompleteListener(task -> callback.accept(task.getResult().exists()));
    }
}
