package com.example.cscb07.data.models;

import com.example.cscb07.data.models.Event;

import java.util.List;

public class Court {
    List<Event> upcomingEvents;
    List<String> sports;

    public Court() {
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

    public String eventsToString(){
        String str = "";
        for(Event event: this.getUpcomingEvents()){
            str = str.concat(event.toString());
        }
        return str;
    }

    @Override
    public String toString(){
        String str = "";
        if(this.getSports()==null)
            return str;
        for(String sport: this.getSports()){
            str = str.concat("\n" + sport);
        }
        return str;
    }
}
