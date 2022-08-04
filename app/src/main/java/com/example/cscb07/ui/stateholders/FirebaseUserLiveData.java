package com.example.cscb07.ui.stateholders;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import org.jetbrains.annotations.NotNull;

public class FirebaseUserLiveData extends LiveData<FirebaseUser> implements FirebaseAuth.AuthStateListener {
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onActive() {
        super.onActive();
        mAuth.addAuthStateListener(this);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        mAuth.removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull @NotNull FirebaseAuth firebaseAuth) {
        setValue(firebaseAuth.getCurrentUser());
    }
}
