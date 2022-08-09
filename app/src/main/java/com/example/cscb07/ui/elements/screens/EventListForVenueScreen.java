package com.example.cscb07.ui.elements.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.cscb07.R;
import com.example.cscb07.ui.elements.screens.eventlist.AbstractEventListScreen;
import com.example.cscb07.ui.state.VenueUiState;
import org.jetbrains.annotations.NotNull;

public class EventListForVenueScreen extends AbstractEventListScreen {
    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.screen_events_for_venue, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VenueUiState venue = EventListForVenueScreenArgs.fromBundle(requireArguments()).getVenue();
        TitleBarUtil.setupToolbar(this).setTitle(venue.name);
        eventListViewModel.loadPendingEventsForVenue(venue.id);
        eventListViewModel.loadVenueEvents(venue.id);
        Button fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(v -> navController.navigate(EventListForVenueScreenDirections.actionScreenHomeToDialogAddEvent(venue)));
        setupList();
    }

    @Override
    public boolean showVenueTitle() {
        return false;
    }
}
