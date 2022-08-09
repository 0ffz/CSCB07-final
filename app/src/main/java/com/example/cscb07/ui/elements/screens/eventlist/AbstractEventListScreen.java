package com.example.cscb07.ui.elements.screens.eventlist;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cscb07.R;
import com.example.cscb07.ui.stateholders.AuthViewModel;
import com.example.cscb07.ui.stateholders.UpcomingListViewModel;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public abstract class AbstractEventListScreen extends Fragment {
    protected UpcomingListViewModel upcomingListViewModel;
    protected AuthViewModel authViewModel;

    protected NavController navController;
    RecyclerView eventsContainer;

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        upcomingListViewModel = new ViewModelProvider(requireActivity()).get(UpcomingListViewModel.class);
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        navController = Navigation.findNavController(view);
        eventsContainer = view.findViewById(R.id.events_container);
    }

    public void setupList(boolean showVenue) {
        authViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user.isAdmin) {
                eventsContainer.setAdapter(new ConcatAdapter(createPendingAdapter(showVenue), createEventAdapter(showVenue)));
            } else {
                eventsContainer.setAdapter(createEventAdapter(showVenue));
            }
            eventsContainer.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        });

    }

    private PendingEventCardAdapter createPendingAdapter(boolean showVenue) {
        PendingEventCardAdapter pendingEventCardAdapter = new PendingEventCardAdapter(Collections.emptyList(), showVenue);
        upcomingListViewModel.getPendingEvents().observe(getViewLifecycleOwner(), pendingEventCardAdapter::setEventList);

        pendingEventCardAdapter.approveClickListener = (eventState, position) -> upcomingListViewModel.approveEvent(eventState.eventId, () -> {
            pendingEventCardAdapter.remove(position);
        });
        pendingEventCardAdapter.denyClickListener = (eventState, position) -> upcomingListViewModel.denyEvent(eventState.eventId, () -> {
            pendingEventCardAdapter.remove(position);
        });
        return pendingEventCardAdapter;
    }

    private EventCardAdapter createEventAdapter(boolean showVenue) {
        EventCardAdapter eventCardAdapter = new EventCardAdapter(Collections.emptyList(), showVenue);
        upcomingListViewModel.getEvents().observe(getViewLifecycleOwner(), eventCardAdapter::setEventList);

        eventCardAdapter.clickListener = (eventState, position) -> {
            upcomingListViewModel.joinEvent(eventState.eventId, () -> {
                eventState.joined = true;
                eventState.attendeeCount += 1;
                eventCardAdapter.notifyItemChanged(position);
            });
        };
        return eventCardAdapter;
    }
}
