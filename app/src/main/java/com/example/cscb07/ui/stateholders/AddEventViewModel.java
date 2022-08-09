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
    //TODO have separate class for handling messages
    private final MutableLiveData<Boolean> attemptingAddEvent = new MutableLiveData<>(false);

    public AddEventViewModel(VenueId currentVenue) {
        this.currentVenue = currentVenue;
    }

    boolean validate(VenueId venue, String name, String description, int maxCapacity) {
        Date s_date = startDate.getValue();
        TimeUiState s_time = startTime.getValue();
        Date e_date = endDate.getValue();
        TimeUiState e_time = endTime.getValue();
        if (venue.venueId.isEmpty() || name.isEmpty() ||
                s_date != null || s_time != null ||
                e_date != null || e_time != null) {
            MessageUtil.showError(R.string.error_empty);
            return false;
        }

        if (maxCapacity < 0) {
            MessageUtil.showError(R.string.error_capacity_value);
            return false;
        }

        if (calculateDateMillis(s_date, s_time) > calculateDateMillis(e_date, e_time)) {
            MessageUtil.showError(R.string.error_invalid_date_range);
            return false;
        }

        return true;
    }

    private void handleAddEventResult(Try<EventId> result) {
        attemptingAddEvent.setValue(false);
        result.onSuccess(createdEvent::postValue);
        result.onFailure(MessageUtil::showError);
    }

    public void addEvent(VenueId venue, String name, String description,
                         String startDate, String startTime,
                         String endDate, String endTime,
                         int maxCapacity) {
        if(!validate(venue, name, description, maxCapacity)) return;
        if(attemptingAddEvent.getValue()) return;
        attemptingAddEvent.setValue(true);
        eventRepository.addEvent(currentVenue, name, description,
                calculateDateMillis(getStartDate().getValue(), getStartTime().getValue()),
                calculateDateMillis(getEndDate().getValue(), getEndTime().getValue()), maxCapacity, this::handleAddEventResult);
    }

    public LiveData<Boolean> isAttemptingAddEvent() { return attemptingAddEvent; }

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
}
