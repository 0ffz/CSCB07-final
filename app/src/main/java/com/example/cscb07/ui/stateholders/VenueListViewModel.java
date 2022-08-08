package com.example.cscb07.ui.stateholders;

import android.media.metrics.Event;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cscb07.R;
import com.example.cscb07.data.models.EventModel;
import com.example.cscb07.data.repositories.EventRepository;
import com.example.cscb07.data.repositories.VenueRepository;
import com.example.cscb07.data.results.EventId;
import com.example.cscb07.data.results.VenueId;
import com.example.cscb07.data.results.WithId;
import com.example.cscb07.data.util.MessageUtil;
import com.example.cscb07.data.util.ServiceLocator;

import java.util.*;
import java.util.stream.Collectors;

import com.example.cscb07.ui.state.EventUiState;
import com.example.cscb07.ui.state.VenueUiState;

import io.vavr.Value;
import io.vavr.control.Try;

public class VenueListViewModel {
    private final VenueRepository venueRepository = ServiceLocator.getInstance().getVenueRepository();
    private final MutableLiveData<List<VenueUiState>> venues = new MutableLiveData<>();

    public LiveData<List<VenueUiState>> getVenues() {
        return venues;
    }

    public void loadVenues() {
        venueRepository.getVenues(null, 10, "", result -> {
            result.onSuccess(newVenues -> {
                venues.setValue(newVenues.stream()
                        .map(it -> new VenueUiState(it.model.name, it.model.description, it.id))
                        .collect(Collectors.toList()));
            });
            result.onFailure(f -> MessageUtil.showError(R.string.error_fail_to_get_events));
        });
    }
}
