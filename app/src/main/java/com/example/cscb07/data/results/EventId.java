package com.example.cscb07.data.results;

import java.util.Objects;

public class EventId {
    public final String eventId;

    public EventId(String id) {
        this.eventId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventId eventId1 = (EventId) o;
        return Objects.equals(eventId, eventId1.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId);
    }
}
