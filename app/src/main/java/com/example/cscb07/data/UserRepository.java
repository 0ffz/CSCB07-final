package com.example.cscb07.data;

import androidx.lifecycle.LiveData;

import com.example.cscb07.data.Results.LoginResult;

public interface UserRepository {
    void registerUser(String email, String password);

    void signIn(String email, String password);
}
