package com.example.cscb07.data.models;

public class UserModel {
    public String password;
    public String email;
    public boolean admin;

    public UserModel(String email, String password, boolean admin) {
        this.email = email;
        this.password = password;
        this.admin = admin;
    }
}
