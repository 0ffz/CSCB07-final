package com.example.cscb07.data.repositories;

import com.example.cscb07.data.RepositoryCallback;
import com.google.firebase.auth.FirebaseUser;
import io.vavr.control.Try;

public interface UserRepository {
    void registerUser(String email, String password, RepositoryCallback<Try<FirebaseUser>> callback);

    void signIn(String email, String password, RepositoryCallback<Try<FirebaseUser>> callback);
}
