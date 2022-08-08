package com.example.cscb07.ui.elements.screens.venuelist;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.cscb07.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.function.Consumer;

public class DialogAddEvent extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add_event, container, false);
    }

    public void showDatePicker(Consumer<Date> consumer) {
        MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.dialog_select_date)
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();
        picker.addOnPositiveButtonClickListener(result -> consumer.accept(new Date(result)));
        picker.show(requireActivity().getSupportFragmentManager(), "date");
    }

    public void showTimePicker(Consumer<Long> consumer) {
        MaterialTimePicker picker = new MaterialTimePicker.Builder()
                .setTitleText(R.string.dialog_select_time)
                .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                .build();
        // TODO pass TimeUiState object
        picker.addOnPositiveButtonClickListener(result -> consumer.accept(-1L));
        picker.show(requireActivity().getSupportFragmentManager(), "clock");
    }

    public void setupField(EditText field, Runnable onSelected) {
        field.setInputType(InputType.TYPE_NULL);
        field.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) onSelected.run();
        });
        field.setOnClickListener((v) -> onSelected.run());
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        TitleBarUtil.setupTitleBar(this);

        EditText startDate = view.findViewById(R.id.event_start_date);
        EditText startTime = view.findViewById(R.id.event_start_time);
        EditText endDate = view.findViewById(R.id.event_end_date);
        EditText endTime = view.findViewById(R.id.event_end_time);

        setupField(startDate, () -> showDatePicker(date -> { /* TODO set date in viewmodel */ }));
        setupField(startTime, () -> showTimePicker(time -> {
        }));
        setupField(endDate, () -> showDatePicker(date -> {
        }));
        setupField(endTime, () -> showTimePicker(time -> {
        }));

        //TODO observe LiveData from viewmodel
//        date.setText(SimpleDateFormat.getDateInstance().format(new Date(result)));
//        time.setText(picker.getHour() + ":" + picker.getMinute());
    }
}
