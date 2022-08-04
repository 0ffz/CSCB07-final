package com.example.cscb07.data.models;

public class UserModel {
    public String email;
    public boolean admin;

    public UserModel() {
    }

    public UserModel(String email, boolean admin) {
        this.email = email;
        this.admin = admin;
    }
}
