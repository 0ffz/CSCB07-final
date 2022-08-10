package com.example.cscb07.ui.elements.screens.venuelist;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cscb07.R;
import com.google.android.material.card.MaterialCardView;

public class VenueCard extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView description;
    public MaterialCardView cardView;

    public VenueCard(View view) {
        super(view);
        name = view.findViewById(R.id.venueTitle);
        description = view.findViewById(R.id.venueDescription);
        cardView = view.findViewById(R.id.venueCardView);
    }
}
