package com.example.cscb07.data.Results;

import java.util.List;

public class VenueResult {
    public final boolean success;
    public final String name;
    public final List<String> courts;

    public VenueResult(boolean success, String name, List<String> courts) {
        this.name=name;
        this.success=success;
        this.courts=courts;
    }
}
