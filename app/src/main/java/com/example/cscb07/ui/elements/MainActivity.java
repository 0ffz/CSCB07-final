package com.example.cscb07.ui.elements;

import android.database.Observable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.cscb07.R;
import com.example.cscb07.data.Court;
import com.example.cscb07.data.Results.VenueResult;
import com.example.cscb07.data.ServiceLocator;
import com.example.cscb07.data.VenueRepository;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;
import static androidx.navigation.ui.NavigationUI.setupWithNavController;

import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setupWithNavController(toolbar, navController, appBarConfiguration);
        setupWithNavController(bottomNav, navController);

        // Hide bottom bar on login screen
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int id = destination.getId();
            if (id == R.id.screenLogin) {
                getSupportActionBar().hide();
                bottomNav.setVisibility(View.GONE);
            } else {
                getSupportActionBar().show();
                bottomNav.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        // Ensures the back button follows navigation logic
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}
