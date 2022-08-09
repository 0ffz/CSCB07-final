package com.example.cscb07.ui.elements.screens;

import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.cscb07.R;

public class TitleBarUtil {
    public static Toolbar setupToolbar(Fragment fragment) {
        View view = fragment.requireView();
//        Activity activity = fragment.requireActivity();
        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        Toolbar toolbar = view.findViewById(R.id.materialToolbar);
//        AppBarLayout appbarLayout = view.findViewById(R.id.appBarLayout);
//        appbarLayout.addLiftOnScrollListener((elevation, backgroundColor) -> {
//            activity.getWindow().setStatusBarColor(backgroundColor);
//        });
//        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        toolbar.setTitle(navController.getCurrentDestination().getLabel());
        toolbar.setNavigationOnClickListener(v -> navController.navigateUp());
//        activity.getWindow().setStatusBarColor(((MaterialShapeDrawable) appbarLayout.getBackground()).getResolvedTintColor());
        return toolbar;
    }
}
