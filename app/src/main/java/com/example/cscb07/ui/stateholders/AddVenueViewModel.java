package com.example.cscb07.ui.stateholders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cscb07.R;
import com.example.cscb07.data.repositories.VenueRepository;
import com.example.cscb07.data.results.VenueId;
import com.example.cscb07.data.util.MessageUtil;
import com.example.cscb07.data.util.ServiceLocator;

import io.vavr.control.Try;

public class AddVenueViewModel extends ViewModel {

    private final VenueRepository venueRepository = ServiceLocator.getInstance().getVenueRepository();
    //TODO have a separate class for handling messages
    private final MutableLiveData<VenueId> createdVenue = new MutableLiveData<>();

    private final MutableLiveData<Boolean> attemptingAddVenue = new MutableLiveData<>(false);

    // checks if dialog fields empty (do smt about listof added sports)
    boolean validate(String name, String description) {
        if (name.isEmpty()) {
            MessageUtil.showError(R.string.error_empty);
            return false;
        }

        if (description.isEmpty()) {
            MessageUtil.showError(R.string.error_empty);
            return false;
        }

        return true;
    }

    private void handleAddVenueResult(Try<VenueId> result) {
        attemptingAddVenue.setValue(false);
        result.onSuccess(createdVenue::postValue);
        result.onFailure(MessageUtil::showError);
    }

    public void addVenue(String name, String description) {
        if (!validate(name, description)) return;
        if(attemptingAddVenue.getValue()) return;
        attemptingAddVenue.setValue(true);
        venueRepository.addVenue(name, description, this::handleAddVenueResult);
    }

    public LiveData<Boolean> isAttemptingAddVenue() {
        return attemptingAddVenue;
    }
}
