package com.example.cscb07.data.repositories.impl;

import com.example.cscb07.R;
import com.example.cscb07.data.RepositoryCallback;
import com.example.cscb07.data.models.UserModel;
import com.example.cscb07.data.repositories.UserRepository;
import com.example.cscb07.data.results.LoginResult;
import com.example.cscb07.data.util.FirebaseUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import kotlin.Result;
import org.jetbrains.annotations.NotNull;

public class FirebaseUserRepository implements UserRepository {
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    public void registerUser(@NotNull String email, @NotNull String password, RepositoryCallback<LoginResult> callback) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                UserModel user = new UserModel(email, password, false);
                FirebaseUtil.getUsers().child(auth.getCurrentUser().getUid()).setValue(user);
                callback.onComplete(new LoginResult(auth.getCurrentUser(), null));
            } else {
                callback.onComplete(new LoginResult(null, task.getException().getLocalizedMessage()));
            }
        });
    }

    public void signIn(@NotNull String email, @NotNull String password, RepositoryCallback<LoginResult> callback) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onComplete(new LoginResult(auth.getCurrentUser(), null));
            } else {
                callback.onComplete(new LoginResult(null, task.getException().getLocalizedMessage()));
            }
        });
    }
}
