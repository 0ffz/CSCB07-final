package com.example.cscb07.ui.stateholders;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cscb07.data.repositories.EventRepository;
import com.example.cscb07.data.results.EventId;
import com.example.cscb07.data.results.VenueId;
import com.example.cscb07.data.util.MessageUtil;
import com.example.cscb07.data.util.ServiceLocator;
import com.example.cscb07.ui.state.TimeUiState;

import java.util.Date;

import io.vavr.control.Try;

public class AddEventViewModel extends ViewModel {
    private final EventRepository eventRepository = ServiceLocator.getInstance().getEventRepository();

    private final MutableLiveData<VenueId> currentVenue = new MutableLiveData<>();
    private final MutableLiveData<Date> startDate = new MutableLiveData<>();
    private final MutableLiveData<TimeUiState> startTime = new MutableLiveData<>();
    private final MutableLiveData<Date> endDate = new MutableLiveData<>();
    private final MutableLiveData<TimeUiState> endTime = new MutableLiveData<>();

    private final MutableLiveData<EventId> createdEvent = new MutableLiveData<>();
    //TODO have separate class for handling messages
    private final MutableLiveData<Boolean> attemptingAddEvent = new MutableLiveData<>(false);

    boolean validate(VenueId venue, String name, String description,
                     String startDate, String startTime,
                     String endDate, String endTime) {
        //TODO
        return false;
    }

    private void handleAddEventResult(Try<EventId> result) {
        attemptingAddEvent.setValue(false);
        result.onSuccess(createdEvent::postValue);
        result.onFailure(MessageUtil::showError);
    }

    public void addEvent(VenueId venue, String name, String description,
                         String startDate, String startTime,
                         String endDate, String endTime) {
        if(!validate(venue, name, description, startDate, startTime, endDate, endTime)) return;
        if(attemptingAddEvent.getValue()) return;
        eventRepository.addEvent(null, null, null, 0, 0, 0, this::handleAddEventResult);
    }
    // notes: need setter and getters for startDate, startTime, endDate, endTime
}
