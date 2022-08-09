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

public abstract class AbstractEventListScreen extends Fragment {
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

    public void setupList(boolean showVenue) {
        authViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user.isAdmin && pendingEventsContainer != null) createPendingList(showVenue);
            createEventList(showVenue);
        });
    }

    private void createPendingList(boolean showVenue) {
        upcomingListViewModel.getPendingEvents().observe(getViewLifecycleOwner(), events -> {
            PendingEventCardAdapter eventCardAdapter = new PendingEventCardAdapter(events, showVenue);
            eventCardAdapter.approveClickListener = (eventState, position) -> upcomingListViewModel.approveEvent(eventState.eventId, () -> {
                eventCardAdapter.remove(position);
            });
            eventCardAdapter.denyClickListener = (eventState, position) -> upcomingListViewModel.denyEvent(eventState.eventId, () -> {
                eventCardAdapter.remove(position);
            });
            pendingEventsContainer.setAdapter(eventCardAdapter);
            pendingEventsContainer.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        });
    }

    private void createEventList(boolean showVenue) {
        upcomingListViewModel.getEvents().observe(getViewLifecycleOwner(), events -> {
            EventCardAdapter eventCardAdapter = new EventCardAdapter(events, showVenue);
            eventCardAdapter.clickListener = (eventState, position) -> {
                upcomingListViewModel.joinEvent(eventState.eventId, () -> {
                    eventState.joined = true;
                    eventCardAdapter.notifyItemChanged(position);
                });
            };
            eventsContainer.setAdapter(eventCardAdapter);
            eventsContainer.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        });
    }
}
