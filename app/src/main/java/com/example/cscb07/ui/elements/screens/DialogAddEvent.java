package com.example.cscb07.ui.elements.screens;

import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.cscb07.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class DialogAddEvent extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add_event, container, false);
    }

    public void showDatePicker(TextView date) {
            MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();
            picker.addOnPositiveButtonClickListener(result -> {
                date.setText(SimpleDateFormat.getDateInstance().format(new Date(result)));
            });
            picker.show(requireActivity().getSupportFragmentManager(), "date");
    }

    public void showTimePicker(TextView time) {
        MaterialTimePicker picker = new MaterialTimePicker.Builder()
                .setTitleText("Select time")
                .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                .build();
        picker.addOnPositiveButtonClickListener(result -> {
            time.setText(picker.getHour() + ":" + picker.getMinute());
        });
        picker.show(requireActivity().getSupportFragmentManager(), "clock");
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        TitleBarUtil.setupTitleBar(this);
        TextInputLayout layout = view.findViewById(R.id.event_start_date_layout);
//        layout.oninp
        EditText startDate = view.findViewById(R.id.event_start_date);
        startDate.setInputType(InputType.TYPE_NULL);
        startDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) showDatePicker(startDate);
        });
        startDate.setOnClickListener((v) -> showDatePicker(startDate));

        EditText startTime = view.findViewById(R.id.event_start_time);
        startTime.setInputType(InputType.TYPE_NULL);
        startTime.setOnClickListener(v -> showTimePicker(startTime));
        startTime.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) showTimePicker(startTime);
        });
    }
}
