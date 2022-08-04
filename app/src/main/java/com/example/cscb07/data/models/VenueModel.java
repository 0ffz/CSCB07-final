package com.example.cscb07.data.models;

import java.util.List;

public class VenueModel {
    public String name;
    public List<String> courts;

    public VenueModel(String name, List<String> courts) {
        this.name = name;
        this.courts = courts;
    }

    @Override
    public String toString() {
        String str = "";
        if (name == null && courts == null)
            return str;
        if (name != null) {
            str = name + "\n";
            if (courts != null) {
                for (String court : courts) {
                    str = str.concat(court);
                }
            }
        }
        return str;
    }
}

