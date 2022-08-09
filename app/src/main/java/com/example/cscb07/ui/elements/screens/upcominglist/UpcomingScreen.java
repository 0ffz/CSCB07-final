package com.example.cscb07.ui.elements.screens.upcominglist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cscb07.R;
import com.example.cscb07.ui.elements.screens.TitleBarUtil;
import com.example.cscb07.ui.elements.screens.eventlist.AbstractEventListScreen;
import com.example.cscb07.ui.elements.screens.eventlist.EventCardAdapter;
import com.example.cscb07.ui.stateholders.UpcomingListViewModel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

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
    }
}
