package com.example.cscb07.ui.state;

import java.util.Date;

public class EventUiState {
    String sport;
    Date startDate;
    Date endDate;
    int maxCapacity;
    int attendeeCount;

    boolean isFull() {
        return attendeeCount >= maxCapacity;
    }
}
