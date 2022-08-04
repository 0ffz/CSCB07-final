package com.example.cscb07.data.models;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class VenueModel {
    public String name;
    public List<String> courts;

    public VenueModel() {
    }

    public VenueModel(String name, List<String> courts) {
        this.name = name;
        this.courts = ImmutableList.copyOf(courts);
    }

    @Override
    public String toString() {
        return "VenueModel{" +
                "name='" + name + '\'' +
                ", courts=" + courts +
                '}';
    }
}

