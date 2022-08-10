package com.example.cscb07.data.repositories;

import com.example.cscb07.data.models.EventModel;
import com.example.cscb07.data.results.EventId;
import com.example.cscb07.data.results.VenueId;
import com.example.cscb07.data.results.WithId;

import com.example.cscb07.ui.state.EventUiState;
import io.vavr.control.Try;

import java.util.List;
import java.util.Set;
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

    void removePendingEvent(EventId event);

        void getUpcomingEventsForCurrentUser(
            Consumer<Try<List<WithId<EventId, EventModel>>>> callback
    );

    void getAllUpcomingEvents(
            Consumer<Try<List<WithId<EventId, EventModel>>>> callback
    );

    void getEventsForVenue(
        VenueId venue,
        Consumer<Try<List<WithId<EventId, EventModel>>>> callback
    );

    void getAllPendingEvents(Consumer<Try<List<WithId<EventId, EventModel>>>> callback);

    void getPendingEventsForVenue(VenueId venue, Consumer<Try<List<WithId<EventId, EventModel>>>> callback);

    void mapToEventUiState(
            List<WithId<EventId, EventModel>> events,
            Consumer<Try<List<EventUiState>>> callback
    );
}
