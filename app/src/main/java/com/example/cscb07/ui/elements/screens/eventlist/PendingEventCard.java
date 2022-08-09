package com.example.cscb07.ui.elements.screens.eventlist;

import android.view.View;
import android.widget.Button;
import com.example.cscb07.R;

public class PendingEventCard extends AbstractEventCard {
    public Button approveButton;
    public Button denyButton;

    public PendingEventCard(View view) {
        super(view);
        approveButton = view.findViewById(R.id.eventApproveButton);
        denyButton = view.findViewById(R.id.eventDenyButton);
    }
}
