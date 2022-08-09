package com.example.cscb07.ui.elements.screens.upcominglist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.cscb07.R;
import com.example.cscb07.ui.elements.screens.TitleBarUtil;
import com.example.cscb07.ui.elements.screens.eventlist.AbstractEventListScreen;
import org.jetbrains.annotations.NotNull;

public class UpcomingScreen extends AbstractEventListScreen {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.screen_upcoming, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TitleBarUtil.setupToolbar(this);
        upcomingListViewModel.loadAllUpcomingEvents();
        upcomingListViewModel.loadAllPendingEvents();
        setupList(true);
    }
}
