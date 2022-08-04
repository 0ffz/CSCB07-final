package com.example.cscb07.data.models;

import com.example.cscb07.data.models.Event;

import java.util.List;

public class CourtModel {
    List<Event> upcomingEvents;
    List<String> sports;

    public CourtModel() {
    }

    public List<String> getSports() {
        return sports;
    }

    public void setSports(List<String> sports) {
        this.sports = sports;
    }

    public List<Event> getUpcomingEvents() {
        return upcomingEvents;
    }

    public void setUpcomingEvents(List<Event> upcomingEvents) {
        this.upcomingEvents = upcomingEvents;
    }
}
