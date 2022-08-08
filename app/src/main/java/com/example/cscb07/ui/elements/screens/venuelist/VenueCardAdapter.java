package com.example.cscb07.ui.elements.screens.venuelist;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cscb07.R;
import com.example.cscb07.ui.state.VenueUiState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.Consumer;

public class VenueCardAdapter extends RecyclerView.Adapter<VenueCard> {
    private final ArrayList<VenueUiState> localDataSet;
    private final Consumer<VenueUiState> clickListener;

    public VenueCardAdapter(ArrayList<VenueUiState> dataSet, Consumer<VenueUiState> clickListener) {
        localDataSet = dataSet;
        this.clickListener = clickListener;
    }

    // Create new views (invoked by the layout manager)
    @NotNull
    @Override
    public VenueCard onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_venue, viewGroup, false);

        return new VenueCard(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(VenueCard venueCard, @SuppressLint("RecyclerView") final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        venueCard.name.setText(localDataSet.get(position).name);
        venueCard.description.setText(localDataSet.get(position).description);
        venueCard.itemView.setOnClickListener(v -> clickListener.accept(localDataSet.get(position)));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
