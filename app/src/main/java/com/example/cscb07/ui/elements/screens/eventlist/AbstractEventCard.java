package com.example.cscb07.ui.elements.screens.eventlist;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cscb07.R;

public abstract class AbstractEventCard extends RecyclerView.ViewHolder {
    public CardView card;
    public TextView title;
    public TextView description;
    public LinearLayout locationContainer;
    public TextView location;
    public TextView time;
    public TextView attendees;

    public AbstractEventCard(View view) {
        super(view);
        card = view.findViewById(R.id.eventCard);
        title = view.findViewById(R.id.eventTitle);
        description = view.findViewById(R.id.eventDescription);
        locationContainer = view.findViewById(R.id.venueTitleContainer);
        location = view.findViewById(R.id.venueTitle);
        time = view.findViewById(R.id.eventStartTime);
        attendees = view.findViewById(R.id.eventAttendees);
    }
}
