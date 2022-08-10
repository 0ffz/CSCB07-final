package com.example.cscb07.ui.elements.screens.eventlist;

import android.view.View;
import android.widget.Button;
import com.example.cscb07.R;

public class EventCard extends AbstractEventCard {
    public Button joinButton;

    public EventCard(View view) {
        super(view);
        joinButton = view.findViewById(R.id.eventJoinButton);
    }
}
