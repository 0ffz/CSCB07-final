package com.example.cscb07.ui.state;

import com.example.cscb07.data.results.VenueId;

public class VenueUiState {
    public final String name;
    public final String description;
    public final VenueId id;

    public VenueUiState(String name, String description, VenueId id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }
}

