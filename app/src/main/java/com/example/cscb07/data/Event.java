package com.example.cscb07.data;

import java.util.Date;
import java.util.List;

public class Event {
    String sport;
    Date startDate;
    Date endDate;
    int maxCapacity;
    List<User> attendees;

    @Override
    public String toString(){
        String str = "";
        if(sport==null||startDate==null||endDate==null){
            return str;
        }
        str = str.concat(sport + "\nstart: " + startDate.toString() + "\nend: " + endDate.toString());
        return str;

    }


}
