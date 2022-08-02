package com.example.cscb07.data.impl;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cscb07.data.LoginResult;
import com.example.cscb07.data.UserRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseUserRepository implements UserRepository {

    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference();
    @Override
    public LiveData<LoginResult> registerUser(String email, String password) {
        MutableLiveData<LoginResult> d = new MutableLiveData<LoginResult>();

        if (email == null || password == null){
            d.setValue(new LoginResult(false, false));
            return d;
        }
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.child("Users").child(email).exists()){
                    //Add New User
                    userRef.child("Users").child(email).child("Password").setValue(password);
                    d.setValue(new LoginResult(true, false));
                }
                else{
                    //Don't add new user
                    d.setValue(new LoginResult(false, false));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //error occurred message
            }
        });
        return d;

    }

    public LiveData<LoginResult> signIn(String email, String password){
        MutableLiveData<LoginResult> d = new MutableLiveData<LoginResult>();

        if (email == null || password == null){
            d.setValue(new LoginResult(false ,false));
            return d;
        }
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Users").child(email).exists()){
                    if (snapshot.child("Users").child(email).child("Password").getValue(String.class).equals(password)){
                        //sign in as User
                        d.setValue((new LoginResult(true, false)));
                    }
                    else{
                        //wrong pswd/email
                        d.setValue(new LoginResult(false, false));
                    }
                }
                else if(snapshot.child("Admin").child(email).exists()){
                    if (snapshot.child("Admin").child(email).child("Password").getValue(String.class).equals(password)){
                        //sign in as Admin
                        d.setValue((new LoginResult(true, true)));
                    }
                    else{
                        //wrong pswd/email
                        d.setValue(new LoginResult(false, false));
                    }
                }
                else{
                    //no User/Admin exists with that email
                    d.setValue(new LoginResult(false, false));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //error occurred message
            }
        });

        return d;
    }
}
