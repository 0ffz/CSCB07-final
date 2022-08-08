package com.example.cscb07.ui.elements.screens.eventlist;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07.R;
import com.example.cscb07.ui.state.EventUiState;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.Consumer;

public class EventCardAdapter extends RecyclerView.Adapter<EventCard>{
    private final ArrayList<EventUiState> localDataSet;
    private final Consumer<EventUiState> clickListener;

    public EventCardAdapter(ArrayList<EventUiState> dataSet, Consumer<EventUiState> clickListener) {
        localDataSet = dataSet;
        this.clickListener = clickListener;
    }

    // Create new views (invoked by the layout manager)
    @NotNull
    @Override
    public EventCard onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_event, viewGroup, false);

        return new EventCard(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(EventCard eventCard, @SuppressLint("RecyclerView") final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        eventCard.title.setText(localDataSet.get(position).name);
        eventCard.description.setText(localDataSet.get(position).description);
        eventCard.location.setText("Venue location"); //this is for the venue location
        String cap = localDataSet.get(position).attendeeCount + "/" + localDataSet.get(position).maxCapacity;
        eventCard.attendees.setText(cap);
        eventCard.time.setText("Date"); //deal with this later
        eventCard.joinButton.setOnClickListener(v -> clickListener.accept(localDataSet.get(position)));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
