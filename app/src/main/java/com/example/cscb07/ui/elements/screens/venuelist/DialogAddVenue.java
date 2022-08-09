package com.example.cscb07.ui.elements.screens.venuelist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.cscb07.R;
import com.example.cscb07.ui.elements.screens.TitleBarUtil;
import com.example.cscb07.ui.stateholders.AddVenueViewModel;
import com.example.cscb07.ui.stateholders.InputValidator;
import com.google.android.material.textfield.TextInputLayout;
import org.jetbrains.annotations.NotNull;

public class DialogAddVenue extends Fragment {

    protected AddVenueViewModel addVenueViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add_venue, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        TitleBarUtil.setupToolbar(this);
        NavController navController = Navigation.findNavController(view);
        InputValidator inputValidator = new InputValidator();
        addVenueViewModel = new ViewModelProvider(requireActivity()).get(AddVenueViewModel.class);

        TextInputLayout venueName = view.findViewById(R.id.venue_name);
        TextInputLayout venueDescription = view.findViewById(R.id.venue_description);

        Button saveVenue = view.findViewById((R.id.buttonSave));

        inputValidator.validate(venueName, s -> {
            if(s.isEmpty()) return getString(R.string.input_error_empty_venue);
            else return null;
        });
        inputValidator.validate(venueDescription, s -> {
            if(s.isEmpty()) return getString(R.string.input_error_empty_venue_desc);
            else return null;
        });

        saveVenue.setOnClickListener(v -> {
            String name = venueName.getEditText().getText().toString();
            String description = venueDescription.getEditText().getText().toString();

            addVenueViewModel.addVenue(name, description, inputValidator);
        });

        addVenueViewModel.getCreatedVenue().observe(getViewLifecycleOwner(), venueId -> {
            navController.popBackStack();
            // TODO move to opened venue
        });
    }
}
