package com.example.cscb07.ui.elements.screens.eventlist;

import android.annotation.SuppressLint;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cscb07.ui.state.EventUiState;

import java.text.DateFormat;
import java.util.Date;
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
        String desc = eventList.get(position).description;
        if(desc.isEmpty())
            eventCard.description.setVisibility(View.GONE);
        else {
            eventCard.description.setText(desc);
            eventCard.description.setVisibility(View.VISIBLE);
        }


        //TODO get venue name
        if (showVenue) eventCard.location.setText(eventList.get(position).venueId.venueId);
        else eventCard.location.setVisibility(View.GONE);
        String cap = eventList.get(position).attendeeCount + "/" + eventList.get(position).maxCapacity;
        eventCard.attendees.setText(cap);

        Date startDate = eventList.get(position).startDate;
        Date endDate = eventList.get(position).endDate;
        String time;
        if (DateFormat.getDateInstance(DateFormat.MEDIUM).format(startDate).equals(DateFormat.getDateInstance(DateFormat.MEDIUM).format(endDate))) {
            // if end date is the same day as start date
            time = DateFormat.getDateInstance(DateFormat.MEDIUM).format(startDate) + " ⋅ " +
                    DateFormat.getTimeInstance(DateFormat.SHORT).format(startDate) + " – " +
                    DateFormat.getTimeInstance(DateFormat.SHORT).format(endDate);
        } else {
            time = DateFormat.getDateInstance(DateFormat.MEDIUM).format(startDate) + " ⋅ " +
                    DateFormat.getTimeInstance(DateFormat.SHORT).format(startDate) + "\n" +
                    DateFormat.getDateInstance(DateFormat.MEDIUM).format(endDate) + " ⋅ " +
                    DateFormat.getTimeInstance(DateFormat.SHORT).format(endDate);
        }
        eventCard.time.setText(time);
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
