package com.example.cscb07.data.results;

import androidx.annotation.Nullable;

public class SignupResult {
    public final boolean success;
    public final @Nullable Integer message;

    public SignupResult(boolean success, @Nullable Integer message) {
        this.success = success;
        this.message = message;
    }
}
