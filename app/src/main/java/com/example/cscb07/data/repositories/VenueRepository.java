package com.example.cscb07.data.repositories;

import com.example.cscb07.data.RepositoryCallback;
import com.example.cscb07.data.models.Court;
import com.example.cscb07.data.results.VenueResult;
import com.example.cscb07.ui.state.VenueUiState;

import java.util.List;

public interface VenueRepository {
    void addVenue(String name, List<Court> courts);
    void readVenue(String name, RepositoryCallback<VenueResult> callback);

    void getVenues(int amount, int page, RepositoryCallback<List<VenueUiState>> callback);
}
