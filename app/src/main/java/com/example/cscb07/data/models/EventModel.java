package com.example.cscb07.data.models;

import com.google.firebase.database.Exclude;

import java.util.Date;

public class EventModel {
    public String name;
    public String venue;
    public String description;
    public long startDateMillis;
    public long endDateMillis;
    public int maxCapacity; // need these public to create a new one and work with it in a different package
    public int numAttendees;

    public EventModel(String name, String venue, String description, long startDateMillis, long endDateMillis, int maxCapacity) {
        this.name = name;
        this.venue = venue;
        this.description = description;
        this.startDateMillis = startDateMillis;
        this.endDateMillis = endDateMillis;
        this.maxCapacity = maxCapacity;
        this.numAttendees = 0;
    }

    @Exclude
    public Date getStartDate() {
        return new Date(startDateMillis);
    }

    @Exclude
    public void setStartDate(Date startDateMillis) {
        this.startDateMillis = startDateMillis.getTime();
    }

    @Exclude
    public Date getEndDate() {
        return new Date(endDateMillis);
    }

    @Exclude
    public void setEndDate(Date endDateMillis) {
        this.endDateMillis = endDateMillis.getTime();
    }

    public EventModel() {
    }

    @Override
    public String toString() {
        return "EventModel{" +
                "name='" + name + '\'' +
                ", venue='" + venue + '\'' +
                ", startDate=" + startDateMillis +
                ", endDate=" + endDateMillis +
                ", maxCapacity=" + maxCapacity +
                ", numAttendees=" + numAttendees +
                '}';
    }
}
