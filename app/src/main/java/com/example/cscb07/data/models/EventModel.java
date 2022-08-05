package com.example.cscb07.data.models;

import java.util.Date;

public class EventModel {
    public String name;
    public String venue;
    private long startDate;
    private long endDate;
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

    public EventModel() {
    }

    public Date getStartDate() {
        return new Date(startDate);
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate.getTime();
    }

    public Date getEndDate() {
        return new Date(endDate);
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate.getTime();
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
