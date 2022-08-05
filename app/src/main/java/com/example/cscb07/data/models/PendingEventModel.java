package com.example.cscb07.data.models;

public class PendingEventModel {
    public EventModel event;
    public String creator;

    public PendingEventModel() {
    }

    public PendingEventModel(EventModel event, String creator) {
        this.event = event;
        this.creator = creator;
    }
}
