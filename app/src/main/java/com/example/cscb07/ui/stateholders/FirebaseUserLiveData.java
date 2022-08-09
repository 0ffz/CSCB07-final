package com.example.cscb07.ui.stateholders;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.cscb07.data.repositories.UserRepository;
import com.example.cscb07.data.util.FirebaseUtil;
import com.example.cscb07.data.util.ServiceLocator;
import com.example.cscb07.ui.state.UserUiState;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import org.jetbrains.annotations.NotNull;

public class FirebaseUserLiveData extends LiveData<UserUiState> implements FirebaseAuth.AuthStateListener {
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final UserRepository userRepository = ServiceLocator.getInstance().getUserRepository();

    public FirebaseUserLiveData() {
        super(new UserUiState(FirebaseAuth.getInstance().getCurrentUser(), true));
    }

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

        // call checkIfAdmin function from userRepos, get isAdmin callback
        // not implemented yet
        userRepository.checkIfAdmin();

        // set true for now
        boolean isAdmin = true;

        setValue(new UserUiState(firebaseAuth.getCurrentUser(), isAdmin));
    }
}
