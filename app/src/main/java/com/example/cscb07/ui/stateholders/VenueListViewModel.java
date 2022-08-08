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
    private final VenueRepository eventRepository = ServiceLocator.getInstance().getEventRepository();
    private final MutableLiveData<VenueUiState> venue = new MutableLiveData<>();
    private final MediatorLiveData<List<VenueUiState>> events = new MediatorLiveData<>();

    public VenueListViewModel() {
        // Clear loaded events when venue changes
        events.addSource(venue, newVenue -> events.setValue(
                // TODO update function call
                eventRepository.getVenues(null, null, null, result -> {
                    result.onSuccess(newEvents -> {
                        events.setValue(
                            newEvents.stream()
                                    .map(it -> new VenueUiState(it.model.name, it.model.description, it.id))
                                    .collect(Collectors.toList());
                        );
                    }).onFailure(f -> MessageUtil.showError(R.string.error_fail_to_get_events));
                });
        ));
    }

    public LiveData<List<EventModel>> getEvents() {
        return events;
    }

    public void loadMoreEvents() {

         eventRepository.getEventsForVenue(lastEvent.getValue(), venueId, 10, lists -> {
             List<WithId<EventId, EventModel>> e = lists.get(); //get list from getEventsForVenue
             List<EventModel> eventList = new ArrayList<>();
             for(WithId<EventId, EventModel> event: e){
                 eventList.add(event.model);
             }
             if(e.size() == 0)
                 lastEvent.setValue(new EventId("")); //reset lastEvent if the list is empty
             else {
                 lastEvent.setValue(e.get(e.size() - 1).id); //sets lastEvent to last id in the list
                 events.setValue(eventList);
             }
        });
    }
}
