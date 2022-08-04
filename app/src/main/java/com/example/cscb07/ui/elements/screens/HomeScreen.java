package com.example.cscb07.ui.elements.screens;

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
import com.example.cscb07.ui.stateholders.LoginViewModel;
import org.jetbrains.annotations.NotNull;

public class HomeScreen extends Fragment {
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.screen_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        LoginViewModel loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        if(!loginViewModel.isAuthenticated())
            navController.navigate(HomeScreenDirections.actionScreenHomeToScreenLogin());
        Button button = view.findViewById(R.id.nextViewButton);
        button.setOnClickListener(v -> navController.navigate(HomeScreenDirections.actionScreenHomeToScreenLogin()));
    }
}
