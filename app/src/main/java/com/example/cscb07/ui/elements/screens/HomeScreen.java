package com.example.cscb07.ui.elements.screens;

import android.os.Bundle;
import android.util.Log;
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
import com.example.cscb07.data.models.VenueModel;
import com.example.cscb07.data.util.FirebaseUtil;
import com.example.cscb07.data.util.MessageUtil;
import com.example.cscb07.ui.stateholders.LoginViewModel;
import com.google.common.collect.ImmutableList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeScreen extends Fragment {
    private NavController navController;
    private LoginViewModel loginViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.screen_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        loginViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user == null) navController.navigate(HomeScreenDirections.actionScreenHomeToScreenLogin());
            else {
                // TODO make home screen
                Button button = view.findViewById(R.id.nextViewButton);
                button.setOnClickListener(v -> {
                });
            }
        });
    }
}
