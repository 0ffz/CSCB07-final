package com.example.cscb07.ui.elements.screens.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.cscb07.R;
import com.example.cscb07.ui.stateholders.InputValidator;
import com.google.android.material.textfield.TextInputLayout;
import org.jetbrains.annotations.NotNull;

public class SignupScreen extends AuthScreen {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.screen_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setupAuthScreen();

        // Info fields
        TextInputLayout email = view.findViewById(R.id.editTextEmail);
        TextInputLayout password = view.findViewById(R.id.editTextPassword);
        TextInputLayout passwordRetype = view.findViewById(R.id.editTextPasswordRetype);
        Button signupButton = view.findViewById(R.id.signupButton);
        Button loginLink = view.findViewById(R.id.loginButton);

        InputValidator inputValidator = new InputValidator();
        String empty = getString(R.string.input_error_empty);

        inputValidator.validateNotEmpty(email, empty);
        inputValidator.validateNotEmpty(password, empty);
        inputValidator.validateNotEmpty(passwordRetype, empty);

        signupButton.setOnClickListener(v -> authViewModel.signUp(
                email.getEditText().getText().toString(),
                password.getEditText().getText().toString(),
                passwordRetype.getEditText().getText().toString(),
                inputValidator
        ));

        // navigate to login page
        loginLink.setOnClickListener(v -> navController.navigate(SignupScreenDirections.actionScreenSignupToScreenLogin()));
    }
}
