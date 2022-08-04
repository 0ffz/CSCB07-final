package com.example.cscb07.data.repositories;

import com.example.cscb07.data.models.EventModel;
import com.example.cscb07.data.results.EventId;
import com.example.cscb07.data.results.VenueId;
import io.vavr.control.Try;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public interface EventRepository {
    void addEvent(
            VenueId venue,
            String eventName,
            String description,
            long startDate,
            long endDate,
            int maxCapacity,
            Consumer<Try<EventId>> callback
    );

    void signUpEvent(EventId event, Consumer<Try<?>> callback);

    void approveEvent(EventId event, Consumer<Try<?>> callback);

    void removeEvent(EventId event, Consumer<Try<?>> callback);

    void getUpcomingEventsForCurrentUser(
            EventId startAt,
            int count,
            Consumer<Try<List<EventModel>>> callback
    );

    void getAllUpcomingEvents(
            EventId startAt,
            int count,
            Consumer<Try<List<EventModel>>> callback
    );
}
