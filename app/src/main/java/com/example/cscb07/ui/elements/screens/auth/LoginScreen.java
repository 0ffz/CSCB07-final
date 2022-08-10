package com.example.cscb07.ui.elements.screens.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.example.cscb07.R;
import com.example.cscb07.ui.stateholders.InputValidator;
import com.google.android.material.textfield.TextInputLayout;
import org.jetbrains.annotations.NotNull;

public class LoginScreen extends AuthScreen {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.screen_login, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        setupAuthScreen();

        TextInputLayout email = view.findViewById(R.id.editTextEmail);
        TextInputLayout password = view.findViewById(R.id.editTextPassword);
        Button loginButton = view.findViewById(R.id.loginButton);
        TextView signupLink = view.findViewById((R.id.signupButton));

        InputValidator inputValidator = new InputValidator();
        String empty = getString(R.string.input_error_empty);

        inputValidator.validateNotEmpty(email, empty);
        inputValidator.validateNotEmpty(password, empty);

        loginButton.setOnClickListener(v -> authViewModel.login(
                email.getEditText().getText().toString(),
                password.getEditText().getText().toString(),
                inputValidator
        ));

        signupLink.setOnClickListener(v -> navController.navigate(LoginScreenDirections.actionScreenLoginToScreenSignup()));
    }
}
