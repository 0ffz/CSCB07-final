package com.example.cscb07.ui.state;

import androidx.annotation.NonNull;
import com.example.cscb07.data.results.EventId;
import com.example.cscb07.data.results.VenueId;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class EventUiState {
    public final String name;
    public final String description;
    public final Date startDate;
    public final Date endDate;
    public int attendeeCount;
    public final int maxCapacity;
    public final EventId eventId;
    public final VenueId venueId;
    //TODO change back to final if we make observers
    public boolean joined;

    public EventUiState(String name, String description, Date startDate, Date endDate, int attendeeCount, int maxCapacity, EventId eventId, VenueId venueId, boolean joined) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.attendeeCount = attendeeCount;
        this.maxCapacity = maxCapacity;
        this.eventId = eventId;
        this.venueId = venueId;
        this.joined = joined;
    }

    boolean isFull() {
        return attendeeCount >= maxCapacity;
    }

}
