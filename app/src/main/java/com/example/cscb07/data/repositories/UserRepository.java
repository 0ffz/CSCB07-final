package com.example.cscb07.data.repositories;

import com.google.firebase.auth.FirebaseUser;
import io.vavr.control.Try;

import java.util.function.Consumer;

public interface UserRepository {
    void registerUser(String email, String password, Consumer<Try<FirebaseUser>> callback);

    void signIn(String email, String password, Consumer<Try<FirebaseUser>> callback);

    void signOutCurrentUser();
}
