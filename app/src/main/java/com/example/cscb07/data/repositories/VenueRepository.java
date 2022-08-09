package com.example.cscb07.data.repositories;

import com.example.cscb07.data.models.VenueModel;
import com.example.cscb07.data.results.VenueId;
import com.example.cscb07.data.results.WithId;
import com.example.cscb07.ui.state.VenueUiState;
import io.vavr.control.Try;

import java.util.List;
import java.util.function.Consumer;

public interface VenueRepository {
    void addVenue(String name, String description, Consumer<Try<VenueUiState>> callback);

    void getVenues(Consumer<Try<List<WithId<VenueId, VenueModel>>>> callback);
}
