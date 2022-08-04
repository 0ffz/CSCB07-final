package com.example.cscb07.data.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class FirebaseUtil {
    public static DatabaseReference getVenues() {
        return ServiceLocator.getInstance().getDb().getReference("Venues");
    }

    public static DatabaseReference getUsers() {
        return ServiceLocator.getInstance().getDb().getReference("Users");
    }

    public static DatabaseReference getEvents(){
        return ServiceLocator.getInstance().getDb().getReference("Events");
    }
}
