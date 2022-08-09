package com.example.cscb07.ui.stateholders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cscb07.data.repositories.EventRepository;
import com.example.cscb07.data.results.EventId;
import com.example.cscb07.data.results.VenueId;
import com.example.cscb07.data.util.MessageUtil;
import com.example.cscb07.data.util.ServiceLocator;
import com.example.cscb07.ui.state.TimeUiState;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.vavr.control.Try;

public class AddEventViewModel extends ViewModel {
    public final VenueId currentVenue;
    private final EventRepository eventRepository = ServiceLocator.getInstance().getEventRepository();

    private final MutableLiveData<Date> startDate = new MutableLiveData<>();
    private final MutableLiveData<TimeUiState> startTime = new MutableLiveData<>();
    private final MutableLiveData<Date> endDate = new MutableLiveData<>();
    private final MutableLiveData<TimeUiState> endTime = new MutableLiveData<>();

    private final MutableLiveData<EventId> createdEvent = new MutableLiveData<>();
    private final MutableLiveData<Boolean> attemptingAddEvent = new MutableLiveData<>(false);

    public AddEventViewModel(VenueId currentVenue) {
        this.currentVenue = currentVenue;
    }

    private void handleAddEventResult(Try<EventId> result) {
        attemptingAddEvent.setValue(false);
        result.onSuccess(createdEvent::postValue);
        result.onFailure(MessageUtil::showMessage);
    }

    public void addEvent(
            String name,
            String description,
            String maxCapacity,
            InputValidator inputValidator
    ) {
        if (!inputValidator.isValid()) return;
        if (attemptingAddEvent.getValue()) return;
        attemptingAddEvent.setValue(true);
        eventRepository.addEvent(currentVenue, name, description,
                calculateDateMillis(getStartDate().getValue(), getStartTime().getValue()),
                calculateDateMillis(getEndDate().getValue(), getEndTime().getValue()), Integer.parseInt(maxCapacity),
                this::handleAddEventResult
        );
    }

    public LiveData<Boolean> isAttemptingAddEvent() {
        return attemptingAddEvent;
    }

    public void setStartDate(Date date) {
        startDate.setValue(date);
    }

    public LiveData<Date> getStartDate() {
        return startDate;
    }

    public void setStartTime(TimeUiState time) {
        startTime.setValue(time);
    }

    public LiveData<TimeUiState> getStartTime() {
        return startTime;
    }

    public void setEndDate(Date date) {
        endDate.setValue(date);
    }

    public LiveData<Date> getEndDate() {
        return endDate;
    }

    public void setEndTime(TimeUiState time) {
        endTime.setValue(time);
    }

    public LiveData<TimeUiState> getEndTime() {
        return endTime;
    }

    private long calculateDateMillis(Date date, TimeUiState time) {
        return date.getTime() + TimeUnit.HOURS.toMillis(time.hour) + TimeUnit.HOURS.toMillis(time.minute);
    }

    public boolean isEndDateValid() {
        Date startDate = getStartDate().getValue();
        TimeUiState startTime = getStartTime().getValue();
        Date endDate = getEndDate().getValue();
        TimeUiState endTime = getEndTime().getValue();
        if (endDate == null || endTime == null || startDate == null || startTime == null) return false;
        // checks if end date comes after start date
        return calculateDateMillis(endDate, endTime) > calculateDateMillis(startDate, startTime);
    }

    public boolean isCapacityValid(String maxCapacity) {
        return Integer.parseInt(maxCapacity) >= 1;
    }

    public LiveData<EventId> getCreatedEvent() {
        return createdEvent;
    }
}
