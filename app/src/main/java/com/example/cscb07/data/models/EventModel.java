package com.example.cscb07.data.models;

import android.media.metrics.Event;

import java.util.Date;
import java.util.List;

public class EventModel {
    public String name;
    public String venue;
    public long startDate;
    public long endDate; //unix time
    public int maxCapacity; // need these public to create a new one and work with it in a different package
    public int numAttendees;

    public EventModel(String name, String venue, long startDate, long endDate, int maxCapacity) {
        this.name = name;
        this.venue = venue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxCapacity = maxCapacity;
        this.numAttendees = 0;
    }

    public EventModel(){

    }

    @Override
    public String toString() {
        return "EventModel{" +
                "name='" + name + '\'' +
                ", venue='" + venue + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", maxCapacity=" + maxCapacity +
                ", numAttendees=" + numAttendees +
                '}';
    }
}
