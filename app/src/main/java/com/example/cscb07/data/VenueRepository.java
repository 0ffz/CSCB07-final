package com.example.cscb07.data;

import androidx.lifecycle.MutableLiveData;
import com.example.cscb07.data.Results.VenueResult;

import java.util.List;

public interface VenueRepository {
    MutableLiveData<VenueResult> addVenue(String name, List<Court> courts);
    MutableLiveData<VenueResult>  readVenues();

}
