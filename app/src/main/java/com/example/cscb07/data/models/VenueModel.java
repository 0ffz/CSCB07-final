package com.example.cscb07.data.models;

import java.util.List;

public class VenueModel {
    public final String name;
    public final List<String> courts;

    public VenueModel(String name, List<Court> courts) {
        this.name = name;
        this.courts = courts;
    }

    @Override
    public String toString() {
        String str = "";
        if(name==null && courts==null)
            return str;
        if(name!=null) {
            str = name + "\n";
            if (courts != null) {
                for (Court court : courts) {
                    str = str.concat(court.toString());
                }
            }
        }
        return str;
    }
}

