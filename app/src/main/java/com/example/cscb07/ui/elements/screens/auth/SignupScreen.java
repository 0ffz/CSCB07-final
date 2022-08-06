package com.example.cscb07.ui.elements.screens.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.cscb07.R;
import com.example.cscb07.ui.stateholders.AuthViewModel;
import org.jetbrains.annotations.NotNull;

public class SignupScreen extends AuthScreen {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.screen_signup, container, false);
    }

    // TODO: Declare FirebaseAuth instance
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setupAuthScreen();

        // Info fields
        EditText editTextEmail = view.findViewById(R.id.editTextEmail);
        EditText editTextPassword = view.findViewById(R.id.editTextPassword);
        EditText editTextPasswordRetype = view.findViewById(R.id.editTextPasswordRetype);
        Button signupButton = view.findViewById(R.id.signupButton);
        Button loginLink = view.findViewById(R.id.loginButton);

        signupButton.setOnClickListener(v -> {
            // Get email/pass as Strings
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            String passwordRetype = editTextPasswordRetype.getText().toString();

            authViewModel.signUp(email, password, passwordRetype);
        });

        // navigate to login page
        loginLink.setOnClickListener(v -> navController.navigate(SignupScreenDirections.actionScreenSignupToScreenLogin()));
    }
}
