package com.example.cscb07.data.results;

import java.util.Objects;

public class EventId {
    public final String key;

    public EventId(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventId eventId1 = (EventId) o;
        return Objects.equals(key, eventId1.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
