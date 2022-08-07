package com.example.cscb07.ui.elements.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.cscb07.R;
import com.example.cscb07.ui.stateholders.AddVenueViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;

public class DialogAddVenue extends Fragment {

    protected AddVenueViewModel addVenueViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add_venue, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        TitleBarUtil.setupTitleBar(this);

        EditText venueName = view.findViewById(R.id.venue_name);
        EditText venueDescription = view.findViewById(R.id.venue_description);
//        EditText venueSportToAdd = view.findViewById(R.id.venue_add_sports);
//        Button addSportButton = view.findViewById((R.id.buttonAddSport));
        Button saveVenue = view.findViewById((R.id.buttonSave));

//        addSportButton.setOnClickListener(v -> {
//            // Get sport to add
//            String sport = venueSportToAdd.getText().toString();
//
//        });

        saveVenue.setOnClickListener(v -> {
            // Get sport to add
            String name = venueName.getText().toString();
            String description = venueDescription.getText().toString();
//            String sports = venueSportToAdd.getText().toString();

            addVenueViewModel.addVenue(name, description);

        });

    }


}
