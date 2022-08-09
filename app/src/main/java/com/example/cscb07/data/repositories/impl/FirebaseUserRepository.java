package com.example.cscb07.data.repositories.impl;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import com.example.cscb07.data.models.UserModel;
import com.example.cscb07.data.models.VenueModel;
import com.example.cscb07.data.repositories.UserRepository;
import com.example.cscb07.data.results.UserId;
import com.example.cscb07.data.results.VenueId;
import com.example.cscb07.data.results.WithId;
import com.example.cscb07.data.util.FirebaseUtil;
import com.example.cscb07.ui.state.UserUiState;
import com.example.cscb07.ui.stateholders.FirebaseUserLiveData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import io.vavr.collection.Stream;
import io.vavr.control.Try;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

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
        DatabaseReference d = FirebaseUtil.getDb(); //get database reference
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        d.child("users").child(user).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    DataSnapshot snap = task.getResult();
                    boolean admin = snap.child("admin").getValue(boolean.class);
                }
            }
        });
    }

}
