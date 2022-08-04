package com.example.cscb07.ui.elements;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.example.cscb07.R;
import com.example.cscb07.data.util.MessageUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;
import static androidx.navigation.ui.NavigationUI.setupWithNavController;

public class MainActivity extends AppCompatActivity {
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up navigation controller with bottom bar
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_container);
        navController = navHostFragment.getNavController();
        setupActionBarWithNavController(this, navController);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        setupWithNavController(bottomNav, navController);
        View coordinatorLayout = findViewById(R.id.coordinatorLayout);

        // Hide bottom bar on login screen
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int id = destination.getId();
            if (id == R.id.screenLogin || id == R.id.screenSignup) {
                getSupportActionBar().hide();
                bottomNav.setVisibility(View.GONE);
            } else {
                getSupportActionBar().show();
                bottomNav.setVisibility(View.VISIBLE);
            }
        });

        MessageUtil.getMessage().observe(this, message -> {
            if (message != null) {
                if (message.messageId == -1) {
                    Snackbar.make(coordinatorLayout, message.message, Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(coordinatorLayout, message.messageId, Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        // Ensures the back button follows navigation logic
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}
