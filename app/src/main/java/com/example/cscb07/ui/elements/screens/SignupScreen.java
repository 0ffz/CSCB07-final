package com.example.cscb07.ui.elements.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.cscb07.R;
import com.example.cscb07.ui.stateholders.SignupViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import org.jetbrains.annotations.NotNull;

public class SignupScreen extends Fragment {
    private SignupViewModel signupViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.screen_signup, container, false);
    }

    // TODO: Declare FirebaseAuth instance
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        signupViewModel = new ViewModelProvider(requireActivity()).get(SignupViewModel.class);
        NavController navController = Navigation.findNavController(view);

        Navigation.findNavController(view);

        // Info fields
        EditText editTextName = view.findViewById(R.id.editTextName);
        EditText editTextEmail = view.findViewById(R.id.editTextEmail);
        EditText editTextPassword = view.findViewById(R.id.editTextPassword);
        EditText editTextPasswordC = view.findViewById(R.id.editTextPasswordC);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        Button signupButton = view.findViewById(R.id.signupButton);
        TextView loginLink = view.findViewById(R.id.loginLink);

        signupButton.setOnClickListener(v -> {
            // Get email/pass as Strings
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            // FireBase

            signupViewModel.signUp(email, password).observe(getViewLifecycleOwner(), login -> {
                //if (login.success) {
                    navController.popBackStack();
                //}
            });
        });
        // TODO: Init FirebaseAuth instance

        // navigate to login page
        loginLink.setOnClickListener(v -> navController.popBackStack());

        // Show error message bar
        signupViewModel.getErrorMessage().observe(getViewLifecycleOwner(), stringId -> {
            Snackbar.make(view, stringId, BaseTransientBottomBar.LENGTH_SHORT).show();
        });
    }
}
