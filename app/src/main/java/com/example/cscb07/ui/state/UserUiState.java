package com.example.cscb07.ui.state;

import com.google.firebase.auth.FirebaseUser;

public class UserUiState {
    public final FirebaseUser user;
    public final boolean isAdmin;

    public UserUiState(FirebaseUser user, boolean isAdmin) {
        this.user = user;
        this.isAdmin = isAdmin;
    }
}
