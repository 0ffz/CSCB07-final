package com.example.cscb07.ui.stateholders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.cscb07.data.repositories.VenueRepository;
import com.example.cscb07.data.util.MessageUtil;
import com.example.cscb07.data.util.ServiceLocator;
import com.example.cscb07.ui.state.VenueUiState;
import io.vavr.control.Try;

public class AddVenueViewModel extends ViewModel {

    private final VenueRepository venueRepository = ServiceLocator.getInstance().getVenueRepository();
    private final MutableLiveData<VenueUiState> createdVenue = new MutableLiveData<>();

    private final MutableLiveData<Boolean> attemptingAddVenue = new MutableLiveData<>(false);

    private void handleAddVenueResult(Try<VenueUiState> result) {
        attemptingAddVenue.setValue(false);
        result.onSuccess(createdVenue::postValue);
        result.onFailure(MessageUtil::showMessage);
    }

    public void addVenue(String name, String description, InputValidator inputValidator) {
        if (!inputValidator.isValid()) return;
        if (attemptingAddVenue.getValue()) return;
        attemptingAddVenue.setValue(true);
        venueRepository.addVenue(name, description, this::handleAddVenueResult);
    }

    public LiveData<Boolean> isAttemptingAddVenue() {
        return attemptingAddVenue;
    }

    public LiveData<VenueUiState> getCreatedVenue() {
        return createdVenue;
    }
}
