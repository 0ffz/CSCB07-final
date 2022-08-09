package com.example.cscb07.data.repositories;

import com.example.cscb07.data.results.EventId;
import com.google.firebase.auth.FirebaseUser;
import io.vavr.control.Try;

import java.util.Set;
import java.util.function.Consumer;

public interface UserRepository {
    void registerUser(String email, String password, Consumer<Try<FirebaseUser>> callback);

    void signIn(String email, String password, Consumer<Try<FirebaseUser>> callback);

    void signOutCurrentUser();

    void checkIfAdmin(Consumer<Boolean> callback);

    void getJoinedEvents(Consumer<Try<Set<EventId>>> callback);
}
