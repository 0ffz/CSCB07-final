package com.example.cscb07.data.results;

import com.example.cscb07.data.models.Court;

import java.util.List;

public class VenueResult {
    public final boolean success;
    public final String name;
    public final List<Court> courts;

    public VenueResult(boolean success, String name, List<Court> courts) {
        this.name=name;
        this.success = success;
        this.courts=courts;
    }

}
