package com.example.cscb07.data.repositories;

import com.example.cscb07.data.RepositoryCallback;
import com.example.cscb07.data.results.LoginResult;

public interface UserRepository {
    void registerUser(String email, String password, RepositoryCallback<LoginResult> callback);

    void signIn(String email, String password, RepositoryCallback<LoginResult> callback);
}
