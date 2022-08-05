package com.example.cscb07.ui.elements.screens.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import com.example.cscb07.R;
import org.jetbrains.annotations.NotNull;

public class LoginScreen extends AuthScreen {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.screen_login, container, false);
    }

    // TODO: Declare FirebaseAuth instance
    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        setupAuthScreen();

        EditText editTextEmail = view.findViewById(R.id.editTextEmail);
        EditText editTextPassword = view.findViewById(R.id.editTextPassword);
        Button loginButton = view.findViewById(R.id.loginButton);
        TextView signupLink = view.findViewById((R.id.signupLink));

        loginButton.setOnClickListener(v -> {
            // Get email/pass as Strings
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            authViewModel.login(email, password);
        });

        signupLink.setOnClickListener(v -> navController.navigate(LoginScreenDirections.actionScreenLoginToScreenSignup()));

    }
}
