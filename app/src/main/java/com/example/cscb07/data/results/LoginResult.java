package com.example.cscb07.data.results;

import com.example.cscb07.ui.state.UserUiState;
import com.google.firebase.auth.FirebaseUser;
import org.jetbrains.annotations.Nullable;

public class LoginResult {
    public final FirebaseUser user;
    public final @Nullable String message;

    public LoginResult(FirebaseUser user, @Nullable String message) {
        this.user = user;
        this.message = message;
    }

    public boolean isSuccessful() {
        return user != null;
    }
}
