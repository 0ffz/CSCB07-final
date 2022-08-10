package com.example.cscb07.ui.elements.screens;

import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import com.example.cscb07.R;

public class TitleBarUtil {
    public static Toolbar setupToolbar(Fragment fragment) {
        View view = fragment.requireView();
        NavController navController = Navigation.findNavController(view);
        Toolbar toolbar = view.findViewById(R.id.materialToolbar);
        NavDestination dest = navController.getCurrentDestination();
        if (toolbar == null || dest == null) return null;
        toolbar.setTitle(dest.getLabel());
        toolbar.setNavigationOnClickListener(v -> navController.navigateUp());
        return toolbar;
    }
}
