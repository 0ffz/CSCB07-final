package com.example.cscb07.data.repositories;

import com.example.cscb07.data.RepositoryCallback;
import com.example.cscb07.data.models.VenueModel;
import com.example.cscb07.ui.state.VenueUiState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface VenueRepository {
    void addVenue(String name);

    void readVenue(@NotNull String name, @NotNull RepositoryCallback<VenueModel> callback);

    void getVenues(int amount, int page, RepositoryCallback<List<VenueUiState>> callback);

}
