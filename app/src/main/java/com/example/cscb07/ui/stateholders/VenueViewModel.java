package com.example.cscb07.ui.stateholders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.cscb07.ui.state.EventUiState;

public class VenueViewModel extends ViewModel {
    private final MutableLiveData<EventUiState> _eventState = new MutableLiveData<>(new EventUiState());
    public final LiveData<EventUiState> eventState = _eventState;

}
