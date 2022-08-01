package com.example.cscb07.data;

import java.util.List;

public class Venue {
    String name;
    List<Court> courts;

    public Venue() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Court> getCourts() {
        return courts;
    }

    public void setCourts(List<Court> courts) {
        this.courts = courts;
    }

    @Override
    public String toString() {
        if(this.getName()==null && this.getCourts()==null)
            return "";
        String str = this.getName() + "\n";
        if(this.getCourts()!=null) {
            for (Court court : this.getCourts()) {
                str = str.concat(court.toString());
            }
        }
        return str;
    }
}

