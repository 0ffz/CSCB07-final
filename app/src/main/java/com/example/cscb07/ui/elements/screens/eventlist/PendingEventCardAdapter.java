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

public class PendingEventCardAdapter extends AbstractEventCardAdapter<PendingEventCard> {
    public BiConsumer<EventUiState, Integer> approveClickListener;
    public BiConsumer<EventUiState, Integer> denyClickListener;
    public BiConsumer<EventUiState, Integer> cardClickListener;

    public PendingEventCardAdapter(List<EventUiState> dataSet, boolean showVenue) {
        super(dataSet, showVenue);
    }

    @NotNull
    @Override
    public PendingEventCard onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_approve, viewGroup, false);
        return new PendingEventCard(view);
    }

    @Override
    public void onBindViewHolder(PendingEventCard pendingCard, @SuppressLint("RecyclerView") final int position) {
        super.onBindViewHolder(pendingCard, position);
        pendingCard.approveButton.setOnClickListener(v -> approveClickListener.accept(eventList.get(position), position));
        pendingCard.denyButton.setOnClickListener(v -> denyClickListener.accept(eventList.get(position), position));
        if (cardClickListener != null) {
            pendingCard.card.setClickable(true);
            pendingCard.card.setOnClickListener(v -> cardClickListener.accept(eventList.get(position), position));
        }
    }
}
