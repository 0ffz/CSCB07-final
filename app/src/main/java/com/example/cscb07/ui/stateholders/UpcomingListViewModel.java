package com.example.cscb07.ui.stateholders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.cscb07.R;
import com.example.cscb07.data.repositories.EventRepository;
import com.example.cscb07.data.results.EventId;
import com.example.cscb07.data.results.VenueId;
import com.example.cscb07.data.util.MessageUtil;
import com.example.cscb07.data.util.ServiceLocator;
import com.example.cscb07.ui.state.EventUiState;

import java.util.List;
import java.util.stream.Collectors;

public class UpcomingListViewModel extends ViewModel {
    private final EventRepository eventRepository = ServiceLocator.getInstance().getEventRepository();
    private final MutableLiveData<List<EventUiState>> events = new MutableLiveData<>();

    public LiveData<List<EventUiState>> getEvents() {
        return events;
    }

    public void loadEvents() {
        eventRepository.getAllUpcomingEvents(result -> {
            result.onSuccess(newVenues -> {
                events.setValue(newVenues.stream()
                        .map(it -> new EventUiState(
                                it.model.name,
                                it.model.description,
                                it.model.getStartDate(),
                                it.model.getEndDate(),
                                it.model.numAttendees,
                                it.model.maxCapacity,
                                it.id,
                                new VenueId(it.model.venue)))
                        .collect(Collectors.toList()));
            });
            result.onFailure(f -> MessageUtil.showMessage(R.string.error_fail_to_get_events));
        });
    }

    public void joinEvent(EventId event) {
        eventRepository.signUpEvent(event, result -> {
            // TODO update ui
//            result.onSuccess((i) -> events.setValue());
            result.onFailure(f -> MessageUtil.showMessage(R.string.error_fail_to_join_event));
        });
    }
}
