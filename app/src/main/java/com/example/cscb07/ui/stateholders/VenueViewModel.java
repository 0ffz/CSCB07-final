package com.example.cscb07.ui.stateholders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.cscb07.ui.state.EventUiState;

public class VenueViewModel extends ViewModel {
    LiveData<EventUiState> eventState = new MutableLiveData<>(new EventUiState());
}
