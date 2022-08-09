package com.example.cscb07.ui.stateholders;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.cscb07.data.results.VenueId;
import org.jetbrains.annotations.NotNull;

public class AddEventViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final VenueId venueId;

    public AddEventViewModelFactory(VenueId venueId) {
        this.venueId = venueId;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
        return (T) new AddEventViewModel(venueId);
    }
}
