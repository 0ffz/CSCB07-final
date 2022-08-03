package com.example.cscb07.data.repositories;

import androidx.lifecycle.LiveData;
import com.example.cscb07.data.models.Court;
import com.example.cscb07.data.results.VenueResult;

import java.util.List;

public interface VenueRepository {
    LiveData<VenueResult> addVenue(String name, List<Court> courts);
    LiveData<VenueResult> readVenue(String name);

}
