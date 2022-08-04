package com.example.cscb07.data.repositories.impl;

import com.example.cscb07.R;
import com.example.cscb07.data.RepositoryCallback;
import com.example.cscb07.data.models.UserModel;
import com.example.cscb07.data.repositories.UserRepository;
import com.example.cscb07.data.util.FirebaseUtil;
import com.example.cscb07.data.util.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import io.vavr.control.Try;
import org.jetbrains.annotations.NotNull;

public class FirebaseUserRepository implements UserRepository {
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    public void registerUser(@NotNull String email, @NotNull String password, RepositoryCallback<Try<FirebaseUser>> callback) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                UserModel user = new UserModel(email, password, false);
                FirebaseUtil.getUsers().child(auth.getCurrentUser().getUid()).setValue(user);
                callback.onComplete(Try.success(auth.getCurrentUser()));
            } else {
                callback.onComplete(Try.failure(task.getException()));
            }
        });
    }

    public void signIn(@NotNull String email, @NotNull String password, RepositoryCallback<Try<FirebaseUser>> callback) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onComplete(Try.success(auth.getCurrentUser()));
            } else {
                callback.onComplete(Try.failure(task.getException()));
            }
        });
    }
}
