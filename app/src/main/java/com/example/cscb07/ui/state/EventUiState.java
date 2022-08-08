package com.example.cscb07.ui.state;

import java.util.Date;

public class EventUiState {
    public final String name;
    public final String description;
    public final Date startDate;
    public final Date endDate;
    public final int attendeeCount;
    public final int maxCapacity;

    public EventUiState(String name, String description, Date startDate, Date endDate, int attendeeCount, int maxCapacity) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.attendeeCount = attendeeCount;
        this.maxCapacity = maxCapacity;
    }

    boolean isFull() {
        return attendeeCount >= maxCapacity;
    }
}
