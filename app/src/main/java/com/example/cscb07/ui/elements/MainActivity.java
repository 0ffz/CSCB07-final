package com.example.cscb07.ui.elements;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.cscb07.R;
import com.example.cscb07.data.Court;
import com.example.cscb07.data.ServiceLocator;
import com.example.cscb07.data.VenueRepository;
import com.example.cscb07.data.impl.FirebaseUserRepository;
import com.example.cscb07.data.impl.FirebaseVenueRepository;
import com.example.cscb07.ui.stateholders.LoginViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;
import static androidx.navigation.ui.NavigationUI.setupWithNavController;

import java.util.List;

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
