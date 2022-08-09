package com.example.cscb07.ui.stateholders;

import android.text.Editable;
import android.text.TextWatcher;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class InputValidator {
    private final Set<TextInputLayout> invalidInputs = new HashSet<>();
    private final Map<TextInputLayout, TextWatcher> watchers = new HashMap<>();

    public void validate(TextInputLayout input) {
        invalidInputs.remove(input);
    }

    public void invalidate(TextInputLayout input) {
        invalidInputs.add(input);
    }

    public boolean isValid() {
        watchers.forEach((layout, watcher) -> {
            watcher.onTextChanged(layout.getEditText().getText().toString(), 0, 0, 0);
        });
        return invalidInputs.isEmpty();
    }

    public void validateNotEmpty(TextInputLayout input, String errorMessage) {
        validate(input, s -> s.isEmpty() ? errorMessage : null);
    }

    public void validate(TextInputLayout inputLayout, Function<String, String> checkForError, TextInputLayout... additional) {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String error = checkForError.apply(s.toString());
                if (error != null) {
                    invalidate(inputLayout);
                    inputLayout.setError(error);
                } else {
                    inputLayout.setError(null);
                    validate(inputLayout);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        for (TextInputLayout input : additional) {
            input.getEditText().addTextChangedListener(watcher);
        }
        watchers.put(inputLayout, watcher);
        inputLayout.getEditText().addTextChangedListener(watcher);
    }

}
