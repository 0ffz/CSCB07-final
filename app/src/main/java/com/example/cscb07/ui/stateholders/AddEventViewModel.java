package com.example.cscb07.ui.stateholders;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cscb07.data.repositories.EventRepository;
import com.example.cscb07.data.results.EventId;
import com.example.cscb07.data.results.VenueId;
import com.example.cscb07.data.util.ServiceLocator;
import com.example.cscb07.ui.state.TimeUiState;

import java.util.Date;

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

    // notes: need setter and getters for startDate, startTime, endDate, endTime
}
