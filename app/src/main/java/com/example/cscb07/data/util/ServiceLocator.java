package com.example.cscb07.data.util;

import com.example.cscb07.data.repositories.EventRepository;
import com.example.cscb07.data.repositories.UserRepository;
import com.example.cscb07.data.repositories.VenueRepository;
import com.example.cscb07.data.repositories.impl.FirebaseEventRepository;
import com.example.cscb07.data.repositories.impl.FirebaseUserRepository;
import com.example.cscb07.data.repositories.impl.FirebaseVenueRepository;
import com.google.firebase.database.FirebaseDatabase;

/**
 * The service locator is a central class for letting the UI access implementations of repositories.
 * <p>
 * Example use {@code ServiceLocator.getInstance().getSomeRepository() }
 */
public class ServiceLocator {

    private static ServiceLocator instance = null;
    private final FirebaseDatabase db = FirebaseDatabase.getInstance("https://cscb07-project-f0f07-default-rtdb.firebaseio.com/");

    private ServiceLocator() {
    }

    public static ServiceLocator getInstance() {
        if (instance == null) {
            synchronized (ServiceLocator.class) {
                instance = new ServiceLocator();
            }
        }
        return instance;
    }

    public UserRepository getUserRepository() {
        return new FirebaseUserRepository();
    }

    public VenueRepository getVenueRepository() {
        return new FirebaseVenueRepository();
    }
    public EventRepository getEventRepository() {
        return new FirebaseEventRepository();
    }

    public FirebaseDatabase getDb() {
        return db;
    }
}
