package com.example.cscb07.ui.elements.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.cscb07.R;
import com.example.cscb07.ui.elements.screens.eventlist.AbstractEventListScreen;
import org.jetbrains.annotations.NotNull;

public class BookingsScreen extends AbstractEventListScreen {
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.screen_bookings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TitleBarUtil.setupToolbar(this);
        eventListViewModel.clearPendingEvents();
        eventListViewModel.loadUpcomingEventsForCurrentUser();
        setupList(true);
    }
}
