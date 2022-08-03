package com.example.cscb07.data.repositories;

import com.example.cscb07.data.RepositoryCallback;
import com.example.cscb07.data.results.LoginResult;
import com.example.cscb07.data.results.SignupResult;

public interface UserRepository {
    void registerUser(String email, String password, RepositoryCallback<SignupResult> callback);

    void signIn(String email, String password, RepositoryCallback<LoginResult> callback);
}
