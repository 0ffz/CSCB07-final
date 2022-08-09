package com.example.cscb07.ui.elements.screens.eventlist;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.cscb07.R;
import com.example.cscb07.ui.elements.screens.TitleBarUtil;
import com.example.cscb07.ui.state.TimeUiState;
import com.example.cscb07.ui.stateholders.AddEventViewModel;
import com.example.cscb07.ui.stateholders.AuthViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

public class DialogAddEvent extends Fragment {
    AddEventViewModel addEventViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        TitleBarUtil.setupTitleBar(this);
        addEventViewModel = new ViewModelProvider(requireActivity()).get(AddEventViewModel.class);

        EditText startDate = view.findViewById(R.id.event_start_date);
        EditText startTime = view.findViewById(R.id.event_start_time);
        EditText endDate = view.findViewById(R.id.event_end_date);
        EditText endTime = view.findViewById(R.id.event_end_time);

        setupField(startDate, () -> showDatePicker(date -> addEventViewModel.setStartDate(date)));
        setupField(startTime, () -> showTimePicker(time -> addEventViewModel.setStartTime(time)));
        setupField(endDate, () -> showDatePicker(date -> addEventViewModel.setEndDate(date)));
        setupField(endTime, () -> showTimePicker(time -> addEventViewModel.setEndTime(time)));

        addEventViewModel.getStartDate().observe(getViewLifecycleOwner(), date -> startDate.setText(SimpleDateFormat.getDateInstance().format(date)));
        addEventViewModel.getStartTime().observe(getViewLifecycleOwner(), time -> startTime.setText(time.toString()));
        addEventViewModel.getEndDate().observe(getViewLifecycleOwner(), date -> endDate.setText(SimpleDateFormat.getDateInstance().format(date)));
        addEventViewModel.getEndTime().observe(getViewLifecycleOwner(), time -> endTime.setText(time.toString()));
    }

    public void showDatePicker(Consumer<Date> consumer) {
        MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.dialog_select_date)
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();
        picker.addOnPositiveButtonClickListener(result -> consumer.accept(new Date(result)));
        picker.show(requireActivity().getSupportFragmentManager(), "date");
    }

    public void showTimePicker(Consumer<TimeUiState> consumer) {
        MaterialTimePicker picker = new MaterialTimePicker.Builder()
                .setTitleText(R.string.dialog_select_time)
                .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                .build();
        // TODO pass TimeUiState object
        picker.addOnPositiveButtonClickListener(result ->
                consumer.accept(new TimeUiState(picker.getHour(), picker.getMinute()))
        );
        picker.show(requireActivity().getSupportFragmentManager(), "clock");
    }

    public void setupField(EditText field, Runnable onSelected) {
        field.setInputType(InputType.TYPE_NULL);
        field.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) onSelected.run();
        });
        field.setOnClickListener((v) -> onSelected.run());
    }

}
