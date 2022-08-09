package com.example.cscb07.ui.elements.screens.eventlist;

import android.annotation.SuppressLint;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cscb07.ui.state.EventUiState;

import java.util.List;

public abstract class AbstractEventCardAdapter<T extends AbstractEventCard> extends RecyclerView.Adapter<T> {

    protected List<EventUiState> eventList;
    protected final boolean showVenue;

    public AbstractEventCardAdapter(List<EventUiState> eventList, boolean showVenue) {
        this.eventList = eventList;
        this.showVenue = showVenue;
    }

    @Override
    public void onBindViewHolder(T eventCard, @SuppressLint("RecyclerView") final int position) {
        eventCard.title.setText(eventList.get(position).name);
        eventCard.description.setText(eventList.get(position).description);
        //TODO get venue name
        if (showVenue) eventCard.location.setText(eventList.get(position).venueId.venueId);
        //TODO hide whole row if no location
        else eventCard.location.setVisibility(View.GONE);
        String cap = eventList.get(position).attendeeCount + "/" + eventList.get(position).maxCapacity;
        eventCard.attendees.setText(cap);
        eventCard.time.setText("Date"); //TODO deal with this later
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void remove(int index) {
        eventList.remove(index);
        notifyItemRemoved(index);
    }

    public void setEventList(List<EventUiState> list) {
        eventList = list;
        notifyDataSetChanged();
    }
}
