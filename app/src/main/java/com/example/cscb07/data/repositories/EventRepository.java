package com.example.cscb07.data.repositories;

import com.example.cscb07.data.models.EventModel;
import com.google.firebase.database.DatabaseReference;

public interface EventRepository {

    void addEvent(String sport, String venue, long startDate, long endDate, int maxCapacity);

    public void signUpForEvent(String uniqEventKey);
}
