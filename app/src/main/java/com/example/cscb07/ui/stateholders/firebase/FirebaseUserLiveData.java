package com.example.cscb07.ui.stateholders.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.example.cscb07.data.repositories.UserRepository;
import com.example.cscb07.data.util.ServiceLocator;
import com.example.cscb07.ui.state.UserUiState;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import org.jetbrains.annotations.NotNull;

public class FirebaseUserLiveData extends LiveData<UserUiState> implements FirebaseAuth.AuthStateListener {
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final UserRepository userRepository = ServiceLocator.getInstance().getUserRepository();

    public FirebaseUserLiveData() {
        super();
        updateUserUiState();
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
        updateUserUiState();
    }

    private void updateUserUiState() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) setValue(null);
        else userRepository.checkIfAdmin(isAdmin -> setValue(new UserUiState(user, isAdmin)));
    }
}
