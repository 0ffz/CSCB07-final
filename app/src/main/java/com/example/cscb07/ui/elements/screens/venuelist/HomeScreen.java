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
        TitleBarUtil.setupToolbar(this);
        navController = Navigation.findNavController(view);
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        venueViewModel = new ViewModelProvider(requireActivity()).get(VenueListViewModel.class);

        // FAB for adding venue (only shows for admins)
        ExtendedFloatingActionButton addVenueButton = view.findViewById(R.id.floatingActionButton);
        authViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user == null) return;
            if (user.isAdmin) addVenueButton.setVisibility(View.VISIBLE);
            else addVenueButton.setVisibility(View.GONE);
        });

        addVenueButton.setOnClickListener(v -> {
            navController.navigate(HomeScreenDirections.actionScreenHomeToDialogAddVenue());
        });

        RecyclerView r = view.findViewById(R.id.venues_container);
        venueViewModel.loadVenues();
        venueViewModel.getVenues().observe(getViewLifecycleOwner(), venues -> {
            VenueCardAdapter venueCardAdapter = new VenueCardAdapter(getContext(), new ArrayList<>(venues), venueState -> {
                navController.navigate(HomeScreenDirections.actionScreenHomeToEventListForVenueScreen(venueState));
            });
            r.setAdapter(venueCardAdapter);
            r.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        });
    }
}
