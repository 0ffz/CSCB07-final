package com.example.cscb07.ui.elements.screens.eventlist;

import android.annotation.SuppressLint;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cscb07.ui.state.EventUiState;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
        if (desc.isEmpty())
            eventCard.description.setVisibility(View.GONE);
        else {
            eventCard.description.setText(desc);
            eventCard.description.setVisibility(View.VISIBLE);
        }

        if (showVenue) {
            eventCard.location.setText(eventList.get(position).venueName);
            eventCard.locationContainer.setVisibility(View.VISIBLE);
        } else eventCard.locationContainer.setVisibility(View.GONE);
        String cap = eventList.get(position).attendeeCount + "/" + eventList.get(position).maxCapacity;
        eventCard.attendees.setText(cap);

        Date startDate = eventList.get(position).startDate;
        Date endDate = eventList.get(position).endDate;
        String time;
        DateFormat shortFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        DateFormat mediumFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);

        if (mediumFormat.format(startDate).equals(mediumFormat.format(endDate))) {
            // if end date is the same day as start date
            time = mediumFormat.format(startDate) + " ⋅ " +
                    shortFormat.format(startDate) + " – " +
                    shortFormat.format(endDate);
        } else {
            time = mediumFormat.format(startDate) + " ⋅ " +
                    shortFormat.format(startDate) + "\n" +
                    mediumFormat.format(endDate) + " ⋅ " +
                    shortFormat.format(endDate);
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
