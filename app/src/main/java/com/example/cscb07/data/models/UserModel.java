package com.example.cscb07.data.models;

public class UserModel {
    public String password;
    public boolean admin;

    public UserModel(String password, boolean admin) {
        this.password = password;
        this.admin = admin;
    }
}
