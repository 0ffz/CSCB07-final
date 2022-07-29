package com.example.cscb07.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cscb07.R;

public class MainActivity extends AppCompatActivity {

    // TODO: Declare FirebaseAuth instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Init FirebaseAuth instance
    }

    @Override
    public void onStart() {
        super.onStart();

        // TODO: Check if user is already signed in and update UI
    }

    // Called when user clicks login text button: Go to signup layer
    public void toSignup(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    // Called when user clicks login button
    public void loginAuth(View view) {
        // Email/password fields
        EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Get email/pass as Strings
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        // If valid email/password fields, authenticate sign up, otherwise set error message
        if (email.length() == 0 || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError(getString(R.string.error_email));
            editTextEmail.requestFocus();
        }
        else if (password.length() == 0) {
            editTextPassword.setError(getString(R.string.error_password));
            editTextPassword.requestFocus();
        }

        else {
            // Show loading progress bar
            progressBar.setVisibility(View.VISIBLE);

            // TODO: Create instance of User class (?)

            // TODO: signin authentication with User instance
        }
    }

}