package com.example.cscb07.data.impl;

import androidx.annotation.NonNull;

import com.example.cscb07.data.User;
import com.example.cscb07.data.UserRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseUserRepository implements UserRepository {

    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users");
    @Override
    public void registerUser(String email, String password) {
        if (email == null || password == null)
            return;
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.child(email).exists()){
                    //Add New User
                    userRef.child(email).child("Password").setValue(password);
                }
                else{
                    //Don't add new user
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //error occurred message
            }
        });

    }

    public void signIn(String email, String password){
        if (email == null || password == null)
            return;
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(email).exists()){
                    if (snapshot.child(email).child("Password").getValue(String.class).equals(password)){
                        if(snapshot.child(email).child("email").getValue(String.class).equals("true")){
                            //sign in as Admin
                        }

                        else{
                            //sign in as User
                        }
                    }
                    else{
                        //wrong pswd/email
                    }
                }
                else{
                    //wrong pswd/email
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //error occurred message
            }
        });
    }
}
