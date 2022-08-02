package com.example.cscb07.data.repositories.impl;

import androidx.annotation.NonNull;
import com.example.cscb07.R;
import com.example.cscb07.data.RepositoryCallback;
import com.example.cscb07.data.models.UserModel;
import com.example.cscb07.data.repositories.UserRepository;
import com.example.cscb07.data.results.LoginResult;
import com.example.cscb07.data.results.SignupResult;
import com.example.cscb07.ui.state.UserUiState;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

public class FirebaseUserRepository implements UserRepository {
    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

    @Override
    public void registerUser(@NotNull String email, @NotNull String password, RepositoryCallback<SignupResult> callback) {
        DatabaseReference userRef = databaseRef.child("users").child(email);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) { //Add New User
                    userRef.setValue(new UserModel(password, false));
                    callback.onComplete(new SignupResult(true, null));
                } else { // User already exists
                    callback.onComplete(new SignupResult(false, R.string.error_user_exists));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onComplete(new SignupResult(false, R.string.error_signup_failed));
            }
        });
    }

    public void signIn(@NotNull String email, @NotNull String password, RepositoryCallback<LoginResult> callback) {
        DatabaseReference userRef = databaseRef.child("users").child(email);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);
                if (userModel != null) {
                    UserUiState user = new UserUiState(email, userModel.admin);
                    if (userModel.password.equals(password)) { //sign in as User
                        callback.onComplete(new LoginResult(user, null));
                    } else { //wrong pswd/email
                        callback.onComplete(new LoginResult(null, R.string.error_wrong_password));
                    }
                } else { //no User/Admin exists with that email
                    callback.onComplete(new LoginResult(null, R.string.error_user_not_exists));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onComplete(new LoginResult(null, R.string.error_login_failed));
            }
        });
    }
}
