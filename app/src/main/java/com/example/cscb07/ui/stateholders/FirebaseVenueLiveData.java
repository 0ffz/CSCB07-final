package com.example.cscb07.ui.stateholders;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import org.jetbrains.annotations.NotNull;

public class FirebaseVenueLiveData extends LiveData<FirebaseUser> {

    public FirebaseVenueLiveData() {
        //
    }

    @Override
    protected void onActive() {
        super.onActive();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }
}
