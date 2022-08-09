package com.example.cscb07.ui.elements.screens.eventlist;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07.R;

public class AdminEventCard extends RecyclerView.ViewHolder{
    public TextView title;
    public TextView description;
    public TextView location;
    public TextView time;
    public TextView attendees;
    public Button cancelButton;
    public Button approveButton;

    public AdminEventCard(View view)
    {
        super(view);
        title = view.findViewById(R.id.eventPendingTitle);
        description = view.findViewById(R.id.eventPendingDescription);
        location = view.findViewById(R.id.venuePendingTitle);
        time = view.findViewById(R.id.eventPendingStartTime);
        attendees = view.findViewById(R.id.eventPendingAttendees);
        cancelButton = view.findViewById(R.id.eventPendingRemoveButton);
        approveButton = view.findViewById(R.id.eventPendingApproveButton);
    }
}
