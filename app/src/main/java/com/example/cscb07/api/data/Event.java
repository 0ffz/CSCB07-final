package com.example.cscb07.api.data;

import java.util.Date;
import java.util.List;

class Event {
    String sport;
    Date startDate;
    Date endDate;
    int maxCapacity;
    List<User> attendees;
}