package com.example.cscb07.data.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class FirebaseUtil {
    public static DatabaseReference getVenues() {
        return ServiceLocator.getInstance().getDb().getReference("venues");
    }

    public static DatabaseReference getUsers() {
        return ServiceLocator.getInstance().getDb().getReference("users");
    }

    public static DatabaseReference getEvents(){
        return ServiceLocator.getInstance().getDb().getReference("events");
    }

    public static DatabaseReference getPendingEvents(){
        return ServiceLocator.getInstance().getDb().getReference("pendingEvents");
    }

    public static DatabaseReference getDb(){
        return ServiceLocator.getInstance().getDb().getReference();
    }
}
