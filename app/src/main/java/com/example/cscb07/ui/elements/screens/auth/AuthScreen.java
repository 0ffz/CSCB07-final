package com.example.cscb07.ui.elements.screens.auth;

import android.view.View;
import android.widget.ProgressBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.cscb07.R;
import com.example.cscb07.ui.stateholders.AuthViewModel;

public class AuthScreen extends Fragment {
    protected AuthViewModel authViewModel;
    protected NavController navController;

    void setupAuthScreen() {
        View view = requireView();
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        navController = Navigation.findNavController(view);

        // Show progress bar while auth is in progress
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        authViewModel.isAttemptingLogin().observe(getViewLifecycleOwner(), inProgress -> {
            if (inProgress) progressBar.setVisibility(View.VISIBLE);
            else progressBar.setVisibility(View.GONE);
        });

        // Go back to previous page when auth succeeds
        authViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) navController.popBackStack();
        });
    }
}
