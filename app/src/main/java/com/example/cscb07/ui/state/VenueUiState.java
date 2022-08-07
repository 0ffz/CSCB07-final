package com.example.cscb07.ui.state;

import java.util.List;

public class VenueUiState {
    //TODO
//    List<String> sports;
//    List<Event> upcomingEvents;
    final String description;
    final String name;

    public VenueUiState(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    public final String getName()
    {
        return this.name;
    }
    public final String getDescription()
    {
        return this.description;
    }
}

