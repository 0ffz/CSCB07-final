package com.example.cscb07.ui.state;

import com.example.cscb07.data.results.VenueId;

import java.io.Serializable;

public class VenueUiState implements Serializable {
    public final String name;
    public final String description;
    public final VenueId id;

    public VenueUiState(String name, String description, VenueId id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }
}

