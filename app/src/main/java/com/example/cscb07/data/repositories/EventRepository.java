package com.example.cscb07.data.repositories;

import com.example.cscb07.data.models.EventModel;
import com.example.cscb07.data.results.EventId;
import com.example.cscb07.data.results.VenueId;
import io.vavr.control.Try;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public interface EventRepository {
    /*
    changed dates to be String, the admin can view and remove the event if the date is incorrect.
     */
    void addEvent(
            String eventName,
            String venue,
            String startDate,
            String endDate,
            int maxCapacity,
            String creator,
            Consumer<Try<EventId>> callback
    );

    void signUpEvent(String user, EventId event, Consumer<Try<?>> callback);

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
