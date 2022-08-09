package com.example.cscb07.ui.elements.screens.eventlist;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cscb07.R;
import com.example.cscb07.ui.stateholders.AuthViewModel;
import com.example.cscb07.ui.stateholders.UpcomingListViewModel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AbstractEventListScreen extends Fragment {
    protected UpcomingListViewModel upcomingListViewModel;
    protected AuthViewModel authViewModel;

    protected NavController navController;
    RecyclerView pendingEventsContainer;
    RecyclerView eventsContainer;

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        upcomingListViewModel = new ViewModelProvider(requireActivity()).get(UpcomingListViewModel.class);
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        navController = Navigation.findNavController(view);
        pendingEventsContainer = view.findViewById(R.id.pending_events_container);
        eventsContainer = view.findViewById(R.id.events_container);
    }

    public void setupList(boolean showVenueName) {
        authViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if(user.isAdmin && pendingEventsContainer != null) createPendingList();
            createEventList();
        });
    }

    private void createPendingList() {
        upcomingListViewModel.getPendingEvents().observe(getViewLifecycleOwner(), events -> {
            EventCardAdapter eventCardAdapter = new EventCardAdapter(new ArrayList<>(events), eventState -> {
                upcomingListViewModel.joinEvent(eventState.eventId);
            });
            eventsContainer.setAdapter(eventCardAdapter);
            eventsContainer.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        });
    }

    private void createEventList() {
        upcomingListViewModel.getEvents().observe(getViewLifecycleOwner(), events -> {
            EventCardAdapter eventCardAdapter = new EventCardAdapter(new ArrayList<>(events), eventState -> {
                upcomingListViewModel.joinEvent(eventState.eventId);
            });
            eventsContainer.setAdapter(eventCardAdapter);
            eventsContainer.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        });
    }
}
