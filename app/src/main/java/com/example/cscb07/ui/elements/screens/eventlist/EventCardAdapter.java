package com.example.cscb07.ui.elements.screens.eventlist;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.cscb07.R;
import com.example.cscb07.ui.state.EventUiState;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;

public class EventCardAdapter extends AbstractEventCardAdapter<EventCard> {
    public BiConsumer<EventUiState, Integer> clickListener;

    public EventCardAdapter(List<EventUiState> dataSet, boolean showVenue) {
        super(dataSet, showVenue);
    }

    @NotNull
    @Override
    public EventCard onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_event, viewGroup, false);
        return new EventCard(view);
    }

    @Override
    public void onBindViewHolder(EventCard eventCard, @SuppressLint("RecyclerView") final int position) {
        super.onBindViewHolder(eventCard, position);
        eventCard.joinButton.setEnabled(!eventList.get(position).joined);
        eventCard.joinButton.setOnClickListener(v -> clickListener.accept(eventList.get(position), position));
    }
}
