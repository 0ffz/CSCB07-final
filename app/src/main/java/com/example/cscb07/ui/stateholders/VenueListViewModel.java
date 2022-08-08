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
    private EventId prev = new EventId("");

    public LiveData<List<EventModel>> getEvents(VenueId venue) {
        MutableLiveData<List<EventModel>> events = new MutableLiveData<>();
         eventRepository.getEventsForVenue(prev, venue, 10, lists -> {
             if(lists.isSuccess()) {
                 List<WithId<EventId, EventModel>> e = lists.get();
                 List<EventModel> eventList = new ArrayList<>();
                 for(WithId<EventId, EventModel> event: e){
                     eventList.add(event.model);
                 }
                 prev = e.get(e.size() - 1).id;
                 events.setValue(eventList);
             }
        });
         return events;
    }

}
