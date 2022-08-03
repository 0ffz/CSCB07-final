package com.example.cscb07.data.results;

import com.example.cscb07.ui.state.UserUiState;
import org.jetbrains.annotations.Nullable;

public class LoginResult {
    public final UserUiState user;
    public final @Nullable Integer message;

    public LoginResult(UserUiState user, @Nullable Integer message) {
        this.user = user;
        this.message = message;
    }

    public boolean isSuccessful() {
        return user != null;
    }
}
