package com.example.cscb07.ui.elements.screens.venuelist;

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
import com.example.cscb07.ui.state.VenueUiState;
import com.example.cscb07.ui.stateholders.AuthViewModel;
import com.example.cscb07.ui.stateholders.VenueListViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeScreen extends Fragment {
    private NavController navController;
    private AuthViewModel authViewModel;
    private VenueListViewModel venueViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.screen_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        TitleBarUtil.setupTitleBar(this);
        navController = Navigation.findNavController(view);
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        venueViewModel = new ViewModelProvider(requireActivity()).get(VenueListViewModel.class);

        // FAB for adding venue
        // TODO conditional only show when admin
        ExtendedFloatingActionButton addVenueButton = view.findViewById(R.id.floatingActionButton);
        addVenueButton.setOnClickListener(v -> {
            navController.navigate(HomeScreenDirections.actionScreenHomeToDialogAddVenue());
        });

        RecyclerView r = view.findViewById(R.id.venues_container);
        venueViewModel.loadVenues();
        venueViewModel.getVenues().observe(getViewLifecycleOwner(), venues -> {
            VenueCardAdapter venueCardAdapter = new VenueCardAdapter(new ArrayList<>(venues), venueState -> {
                // TODO open specific venue model
                // venueState.id
            });
            r.setAdapter(venueCardAdapter);
            r.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        });
    }
}
