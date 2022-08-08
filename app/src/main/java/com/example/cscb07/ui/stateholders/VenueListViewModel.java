package com.example.cscb07.ui.stateholders;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cscb07.data.models.EventModel;
import com.example.cscb07.data.repositories.EventRepository;
import com.example.cscb07.data.results.EventId;
import com.example.cscb07.data.results.VenueId;
import com.example.cscb07.data.results.WithId;
import com.example.cscb07.data.util.ServiceLocator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import io.vavr.Value;
import io.vavr.control.Try;

public class VenueListViewModel {
    private final EventRepository eventRepository = ServiceLocator.getInstance().getEventRepository();
    private EventId lastEvent = new EventId("");

    public LiveData<List<EventModel>> getEvents(VenueId venue) {
        MutableLiveData<List<EventModel>> events = new MutableLiveData<>();
         eventRepository.getEventsForVenue(lastEvent, venue, 10, lists -> {
             List<WithId<EventId, EventModel>> e = lists.get(); //get list from getEventsForVenue
             List<EventModel> eventList = new ArrayList<>();
             for(WithId<EventId, EventModel> event: e){
                 eventList.add(event.model);
             }
             if(e.size() == 0)
                 lastEvent = new EventId(""); //reset lastEvent if the list is empty
             else {
                 lastEvent = e.get(e.size() - 1).id; //sets lastEvent to last id in the list
                 events.setValue(eventList);
             }
        });
         return events;
    }

}
