package com.example.cscb07.ui.stateholders;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class InputValidator {
    private final Set<TextView> invalidInputs = new HashSet<>();

    public void validate(TextView input) {
        invalidInputs.remove(input);
    }

    public void invalidate(TextView input) {
        invalidInputs.add(input);
    }

    public boolean areAllValid() {
        return invalidInputs.isEmpty();
    }

    public void validate(TextInputLayout inputLayout, Function<String, String> checkForError) {
        inputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String error = checkForError.apply(s.toString());
                if(error != null) {
                    invalidate(inputLayout.getEditText());
                    inputLayout.setError(error);
                } else {
                    inputLayout.setError(null);
                    validate(inputLayout.getEditText());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
