package com.example.cscb07.data;

import androidx.lifecycle.LiveData;

import com.example.cscb07.data.Results.LoginResult;

public interface UserRepository {
    LiveData<LoginResult> registerUser(String email, String password);

    LiveData<LoginResult> signIn(String email, String password);
}
