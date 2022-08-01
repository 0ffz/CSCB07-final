package com.example.cscb07.data;

public class LoginResult {
    public final boolean success;
    public final boolean isAdmin;

    public LoginResult(boolean success, boolean isAdmin) {
        this.success = success;
        this.isAdmin = isAdmin;
    }
}
