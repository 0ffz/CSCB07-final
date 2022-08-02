package com.example.cscb07.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.cscb07.data.Results.VenueResult;

import java.util.List;

public interface VenueRepository {
    LiveData<VenueResult> addVenue(String name, List<Court> courts);
    LiveData<VenueResult> readVenue(String name);

}
