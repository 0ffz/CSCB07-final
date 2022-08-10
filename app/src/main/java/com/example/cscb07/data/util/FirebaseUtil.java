package com.example.cscb07.data.util;

import com.example.cscb07.data.results.EventId;
import com.example.cscb07.data.results.VenueId;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class FirebaseUtil {
    public static DatabaseReference getVenues() {
        return ServiceLocator.getInstance().getDb().getReference("venues");
    }

    public static DatabaseReference getVenue(VenueId id) {
        return getVenues().child(id.venueId);
    }

    public static DatabaseReference getUsers() {
        return ServiceLocator.getInstance().getDb().getReference("users");
    }

    public static DatabaseReference getCurrentUserRef() {
        return getUsers().child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    public static DatabaseReference getEvents() {
        return ServiceLocator.getInstance().getDb().getReference("events");
    }

    public static DatabaseReference getEvent(EventId eventId) {
        return getEvents().child(eventId.key);
    }

    public static DatabaseReference getPendingEvents() {
        return ServiceLocator.getInstance().getDb().getReference("pendingEvents");
    }

    public static DatabaseReference getPendingEvent(EventId eventId) {
        return getPendingEvents().child(eventId.key);
    }

    public static DatabaseReference getPendingCreators() {
        return ServiceLocator.getInstance().getDb().getReference("pendingCreators");
    }

    public static DatabaseReference getDb() {
        return ServiceLocator.getInstance().getDb().getReference();
    }
}
