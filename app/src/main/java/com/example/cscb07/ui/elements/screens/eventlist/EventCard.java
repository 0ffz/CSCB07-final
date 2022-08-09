package com.example.cscb07.ui.elements.screens.eventlist;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07.R;

public class EventCard extends RecyclerView.ViewHolder{
    public TextView title;
    public TextView description;
    public TextView location;
    public TextView time;
    public TextView attendees;
    public Button joinButton;

    public EventCard(View view) {
        super(view);
        title = view.findViewById(R.id.eventTitle);
        description = view.findViewById(R.id.eventDescription);
        location = view.findViewById(R.id.venueTitle);
        time = view.findViewById(R.id.eventStartTime);
        attendees = view.findViewById(R.id.eventAttendees);
        joinButton = view.findViewById(R.id.eventJoinButton);
    }
}
