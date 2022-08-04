package com.example.cscb07.data.models;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class VenueModel {

    public String name;
    public String description;
    // And an event list

    public VenueModel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public VenueModel() {
    }
}

