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
import com.example.cscb07.ui.state.EventUiState;
import com.example.cscb07.ui.stateholders.AuthViewModel;
import com.example.cscb07.ui.stateholders.EventListViewModel;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public abstract class AbstractEventListScreen extends Fragment {
    protected EventListViewModel eventListViewModel;
    protected AuthViewModel authViewModel;

    protected NavController navController;
    private RecyclerView eventsContainer;
    protected PendingEventCardAdapter pendingEventCardAdapter;
    protected EventCardAdapter eventCardAdapter;

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        eventListViewModel = new ViewModelProvider(requireActivity()).get(EventListViewModel.class);
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        navController = Navigation.findNavController(view);
        eventsContainer = view.findViewById(R.id.events_container);
    }

    public void setupList() {
        authViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user.isAdmin) {
                eventsContainer.setAdapter(new ConcatAdapter(createPendingAdapter(), createEventAdapter()));
            } else {
                eventsContainer.setAdapter(createEventAdapter());
            }
            eventsContainer.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        });

    }

    private PendingEventCardAdapter createPendingAdapter() {
        pendingEventCardAdapter = new PendingEventCardAdapter(Collections.emptyList(), showVenueTitle());
        eventListViewModel.getPendingEvents().observe(getViewLifecycleOwner(), pendingEventCardAdapter::setEventList);

        pendingEventCardAdapter.approveClickListener = this::onApprove;
        pendingEventCardAdapter.denyClickListener = this::onDeny;
        if(isCardClickable()) pendingEventCardAdapter.cardClickListener = this::onClickCard;
        return pendingEventCardAdapter;
    }

    private EventCardAdapter createEventAdapter() {
        eventCardAdapter = new EventCardAdapter(Collections.emptyList(), showVenueTitle());
        eventListViewModel.getEvents().observe(getViewLifecycleOwner(), eventCardAdapter::setEventList);

        eventCardAdapter.clickListener = this::onJoin;
        return eventCardAdapter;
    }

    boolean showVenueTitle() {
        return true;
    }

    public void onApprove(EventUiState eventState, Integer position) {
        eventListViewModel.approveEvent(eventState.eventId, () -> {
            pendingEventCardAdapter.remove(position);
        });
    }

    private void onDeny(EventUiState eventState, Integer position) {
        eventListViewModel.denyEvent(eventState.eventId, () -> {
            pendingEventCardAdapter.remove(position);
        });
    }

    private void onJoin(EventUiState eventState, Integer position) {
        eventListViewModel.joinEvent(eventState.eventId, () -> {
            eventState.joined = true;
            eventState.attendeeCount += 1;
            eventCardAdapter.notifyItemChanged(position);
        });
    }

    public boolean isCardClickable() {
        return false;
    }

    private void onClickCard(EventUiState eventState, Integer position) {
    }
}
