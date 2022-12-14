package com.example.cscb07.ui.elements;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.example.cscb07.R;
import com.example.cscb07.data.util.MessageUtil;
import com.example.cscb07.ui.elements.screens.venuelist.HomeScreenDirections;
import com.example.cscb07.ui.stateholders.AuthViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.elevation.SurfaceColors;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import static androidx.navigation.ui.NavigationUI.setupWithNavController;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set up navigation controller with bottom bar
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_container);
        navController = navHostFragment.getNavController();
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
//        setSupportActionBar(toolbar);
//        setupActionBarWithNavController(this, navController);
        getWindow().setStatusBarColor(SurfaceColors.getColorForElevation(this, 11));

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        setupWithNavController(bottomNav, navController);
        View coordinatorLayout = findViewById(R.id.coordinatorLayout);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int id = destination.getId();
            boolean isAuthScreen = id == R.id.screenLogin || id == R.id.screenSignup;
            boolean noAuth = authViewModel.getUser().getValue() == null;
            boolean hideTopbar = arguments != null && Objects.equals(arguments.get("hide_topbar"), true);
            boolean hideBottombar = arguments != null && Objects.equals(arguments.get("hide_bottombar"), true);

            if (!isAuthScreen && noAuth) { // Keep user on login page if not logged in
                navController.navigate(HomeScreenDirections.actionGlobalAuthNav());
                return;
            }

            if (hideBottombar)
                bottomNav.setVisibility(View.GONE);
            else
                bottomNav.setVisibility(View.VISIBLE);
        });

        // Force user to login if not logged in
        authViewModel.getUser().observe(this, user -> {
            if (user == null) navController.navigate(HomeScreenDirections.actionGlobalAuthNav());
        });

        // Show snackbar messages
        MessageUtil.getMessage().observe(this, message -> {
            if (message != null) {
                if (message.messageId == -1) {
                    Snackbar.make(coordinatorLayout, message.message, Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(coordinatorLayout, message.messageId, Snackbar.LENGTH_LONG).show();
                }
            }
        });

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.screenLogout) authViewModel.signOut();
            if (bottomNav.getSelectedItemId() == id) {
                navController.popBackStack(id, false);
            } else {
                NavigationUI.onNavDestinationSelected(item, navController);
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Ensures the back button follows navigation logic
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}
