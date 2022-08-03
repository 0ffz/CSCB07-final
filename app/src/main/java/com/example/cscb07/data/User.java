package com.example.cscb07.data;

import java.util.List;

public class User {
    String username, password;
    List<Event> events;

    public User(){

    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
        events = null;
    }

    public void addEvent(Event event){
        events.add(event);
    }
}
