package com.example.cscb07.ui.stateholders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.cscb07.R;
import com.example.cscb07.data.models.EventModel;
import com.example.cscb07.data.repositories.EventRepository;
import com.example.cscb07.data.repositories.UserRepository;
import com.example.cscb07.data.results.EventId;
import com.example.cscb07.data.results.VenueId;
import com.example.cscb07.data.results.WithId;
import com.example.cscb07.data.util.MessageUtil;
import com.example.cscb07.data.util.ServiceLocator;
import com.example.cscb07.ui.state.EventUiState;
import io.vavr.control.Try;

import java.util.List;
import java.util.stream.Collectors;

public class UpcomingListViewModel extends ViewModel {
    private final EventRepository eventRepository = ServiceLocator.getInstance().getEventRepository();
    private final UserRepository userRepository = ServiceLocator.getInstance().getUserRepository();
    private final MutableLiveData<List<EventUiState>> events = new MutableLiveData<>();
    private final MutableLiveData<List<EventUiState>> pendingEvents = new MutableLiveData<>();

    public void loadAllUpcomingEvents() {
        eventRepository.getAllUpcomingEvents(this::setEvents);
    }

    public void loadUpcomingEventsForCurrentUser() {
        eventRepository.getUpcomingEventsForCurrentUser(this::setEvents);
    }

    public void loadVenueEvents(VenueId id) {
        eventRepository.getUpcomingEventsForCurrentUser(this::setEvents);
    }

    public void loadAllPendingEvents() {

    }

    public void loadPendingEventsForVenue(VenueId id) {

    }

    public void joinEvent(EventId event, Runnable callback) {
        eventRepository.signUpEvent(event, result -> {
            result.onSuccess((i) -> callback.run());
            result.onFailure(MessageUtil::showMessage);
        });
    }

    private void setEvents(Try<List<WithId<EventId, EventModel>>> result) {
        userRepository.getJoinedEvents(joinedResult -> {
            joinedResult.onSuccess(joined -> {
                result.onSuccess(newVenues -> events.setValue(newVenues.stream()
                        .map(it -> new EventUiState(
                                it.model.name,
                                it.model.description,
                                it.model.getStartDate(),
                                it.model.getEndDate(),
                                it.model.numAttendees,
                                it.model.maxCapacity,
                                it.id,
                                new VenueId(it.model.venue),
                                joined.contains(it.id)
                        ))
                        .collect(Collectors.toList()))
                );
            });
        });
        result.onFailure(f -> MessageUtil.showMessage(R.string.error_fail_to_get_events));
    }

    public LiveData<List<EventUiState>> getEvents() {
        return events;
    }

    public LiveData<List<EventUiState>> getPendingEvents() {
        return pendingEvents;
    }

}
