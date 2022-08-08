package com.example.cscb07.ui.elements.screens.venuelist;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cscb07.R;

public class VenueCard extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView description;

    public VenueCard(View view) {
        super(view);
        name = view.findViewById(R.id.venueTitle);
        description = view.findViewById(R.id.venueDescription);
    }
}
