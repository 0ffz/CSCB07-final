package com.example.cscb07.data.models;

import android.media.metrics.Event;

import com.example.cscb07.data.results.VenueId;

import java.util.Date;
import java.util.List;

public class EventModel {
    public String name;
    public String venue;
    public String startDate;
    public String endDate; //unix time
    public int maxCapacity; // need these public to create a new one and work with it in a different package
    public int numAttendees;

    public EventModel(String name, String venue, String startDate, String endDate, int maxCapacity) {
        this.name = name;
        this.venue = venue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxCapacity = maxCapacity;
        this.numAttendees = 0;
    }

    public int getNumAttendees() {
        return numAttendees;
    }

    public void setNumAttendees(int numAttendees) {
        this.numAttendees = numAttendees;
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
