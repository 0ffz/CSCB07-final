package com.example.cscb07.ui.elements.screens;

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
import com.example.cscb07.ui.state.VenueUiState;
import com.example.cscb07.ui.stateholders.AuthViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeScreen extends Fragment implements RecyclerAdapt.ItemClickListener {
    private NavController navController;
    private AuthViewModel authViewModel;

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

        // FAB for adding venue
        // TODO conditional only show when admin
        ExtendedFloatingActionButton addVenueButton = view.findViewById(R.id.floatingActionButton);
        addVenueButton.setOnClickListener(v -> {
            navController.navigate(HomeScreenDirections.actionScreenHomeToDialogAddEvent());
        });

        ArrayList<VenueUiState> state = new ArrayList<VenueUiState>();
        for (int i = 0; i < 10; i++) {
            state.add(new VenueUiState("Venue ", "Description"));
        }
        RecyclerView r = view.findViewById(R.id.venues_container);
        RecyclerAdapt recyclerAdapt = new RecyclerAdapt(state, this);
        r.setAdapter(recyclerAdapt);
        r.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }

    @Override
    public void onItemClick(VenueUiState dataModel) {
        //Fragment fragment = EventsFragment.newInstance(dataModel.getName());
        //FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        //transaction.replace(R.id.venues_container, fragment, "events_fragment");

        //transaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("main_fragment"));
        //transaction.add(R.id.frame_container, fragment);
        //transaction.addToBackStack(null);
        //transaction.commit();
    }
}
