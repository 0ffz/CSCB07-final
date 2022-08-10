package com.example.cscb07.ui.elements.screens.eventlist;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.cscb07.R;
import com.example.cscb07.data.util.MessageUtil;
import com.example.cscb07.ui.elements.screens.TitleBarUtil;
import com.example.cscb07.ui.state.TimeUiState;
import com.example.cscb07.ui.state.VenueUiState;
import com.example.cscb07.ui.stateholders.AddEventViewModel;
import com.example.cscb07.ui.stateholders.AddEventViewModelFactory;
import com.example.cscb07.ui.stateholders.InputValidator;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.function.Consumer;

public class DialogAddEvent extends Fragment {
    AddEventViewModel addEventViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        VenueUiState venue = DialogAddEventArgs.fromBundle(requireArguments()).getVenue();
        TitleBarUtil.setupToolbar(this).setTitle(venue.name);
        NavController navController = Navigation.findNavController(view);
        addEventViewModel = new AddEventViewModelFactory(venue.id).create(AddEventViewModel.class);

        // Set up date and time pickers
        TextInputLayout eventName = view.findViewById(R.id.event_name);
        TextInputLayout eventDesc = view.findViewById(R.id.event_description);
        TextInputLayout startDate = view.findViewById(R.id.event_start_date);
        TextInputLayout startTime = view.findViewById(R.id.event_start_time);
        TextInputLayout endDate = view.findViewById(R.id.event_end_date);
        TextInputLayout endTime = view.findViewById(R.id.event_end_time);
        TextInputLayout maxCapacity = view.findViewById(R.id.max_capacity);
        Button saveButton = view.findViewById(R.id.add_event_save_button);

        setupField(startDate, () -> showDatePicker(date -> addEventViewModel.setStartDate(date)));
        setupField(startTime, () -> showTimePicker(time -> addEventViewModel.setStartTime(time)));
        setupField(endDate, () -> showDatePicker(date -> addEventViewModel.setEndDate(date)));
        setupField(endTime, () -> showTimePicker(time -> addEventViewModel.setEndTime(time)));

        // Add input validation for fields
        InputValidator inputValidator = new InputValidator();
        String empty = getString(R.string.input_error_empty);

        inputValidator.validateNotEmpty(eventName, empty);
        inputValidator.validateNotEmpty(startDate, empty);
        inputValidator.validateNotEmpty(startTime, empty);
        inputValidator.validate(endDate, s -> {
            if (s.isEmpty()) return empty;
            else if (!addEventViewModel.isEndDateValid()) return getString(R.string.input_error_end_date_valid);
            else return null;
        }, startDate, startTime, endTime);
        inputValidator.validateNotEmpty(endTime, empty);
        inputValidator.validateNotEmpty(maxCapacity, empty);
        inputValidator.validate(maxCapacity, s -> {
            if (s.isEmpty()) return empty;
            else if (!addEventViewModel.isCapacityValid(s))
                return getString(R.string.input_error_capacity_valid);
            else return null;
        });

        // Observe updates to update text on date and time pickers
        addEventViewModel.getStartDate().observe(getViewLifecycleOwner(), date -> startDate.getEditText().setText(SimpleDateFormat.getDateInstance().format(date)));
        addEventViewModel.getStartTime().observe(getViewLifecycleOwner(), time -> startTime.getEditText().setText(time.toString()));
        addEventViewModel.getEndDate().observe(getViewLifecycleOwner(), date -> endDate.getEditText().setText(SimpleDateFormat.getDateInstance().format(date)));
        addEventViewModel.getEndTime().observe(getViewLifecycleOwner(), time -> endTime.getEditText().setText(time.toString()));
        saveButton.setOnClickListener(v -> addEventViewModel.addEvent(
                eventName.getEditText().getText().toString(),
                eventDesc.getEditText().getText().toString(),
                maxCapacity.getEditText().getText().toString(),
                inputValidator
        ));

        addEventViewModel.getCreatedEvent().observe(getViewLifecycleOwner(), event -> {
            navController.popBackStack();
            MessageUtil.showMessage(R.string.success_event_created);
        });
    }

    public void showDatePicker(Consumer<Date> consumer) {
        MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.dialog_select_date)
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();
        picker.addOnPositiveButtonClickListener(result -> consumer.accept(new Date(result - TimeZone.getDefault().getOffset(result))));
        picker.show(requireActivity().getSupportFragmentManager(), "date");
    }

    public void showTimePicker(Consumer<TimeUiState> consumer) {
        MaterialTimePicker picker = new MaterialTimePicker.Builder()
                .setTitleText(R.string.dialog_select_time)
                .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                .build();
        picker.addOnPositiveButtonClickListener(result ->
                consumer.accept(new TimeUiState(picker.getHour(), picker.getMinute()))
        );
        picker.show(requireActivity().getSupportFragmentManager(), "clock");
    }

    public void setupField(TextInputLayout field, Runnable onSelected) {
        field.getEditText().setInputType(InputType.TYPE_NULL);
        field.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) onSelected.run();
        });
        field.getEditText().setOnClickListener((v) -> onSelected.run());
    }

    private Date addOneDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        return new Date(c.getTimeInMillis());
    }
}
