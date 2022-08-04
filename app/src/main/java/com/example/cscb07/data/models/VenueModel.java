package com.example.cscb07.data.models;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class VenueModel {
    public String name;
    public List<String> sports;
    public List<String> events;

    public VenueModel(String name, List<String> sports, List<String> events) {
        this.name = name;
        this.sports = sports;
        this.events = events;
    }


    public VenueModel() {
    }

    @Override
    public String toString() {
        return "VenueModel{" +
                "name='" + name + '\'' +
                ", sports=" + sports +
                ", events=" + events +
                '}';
    }
}

