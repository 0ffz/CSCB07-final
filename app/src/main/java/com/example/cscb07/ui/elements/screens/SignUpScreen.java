package com.example.cscb07.ui.elements.screens;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.example.cscb07.R;
import com.example.cscb07.ui.stateholders.LoginViewModel;
import org.jetbrains.annotations.NotNull;

public class SignUpScreen extends Fragment {
    private LoginViewModel loginViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.screen_login, container, false);
    }

    // TODO: Declare FirebaseAuth instance
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);


        Navigation.findNavController(view);
        // Email/password fields
        EditText editTextEmail = view.findViewById(R.id.editTextEmail);
        EditText editTextPassword = view.findViewById(R.id.editTextPassword);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        Button signupButton = view.findViewById(R.id.signupButton);

        signupButton.setOnClickListener(v -> {
            // Get email/pass as Strings
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            //TODO this should return an observable that will let us switch
            // to the next screen when we're sure our user exists in database
            loginViewModel.signUp(email, password);
        });
        // TODO: Init FirebaseAuth instance
    }
}
