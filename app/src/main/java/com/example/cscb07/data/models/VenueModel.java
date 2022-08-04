package com.example.cscb07.data.models;

import java.util.List;

public class VenueModel {
    public String name;
    public List<String> courts;

    public VenueModel(String name, List<String> courts) {
        this.name = name;
        this.courts = courts;
    }

}

