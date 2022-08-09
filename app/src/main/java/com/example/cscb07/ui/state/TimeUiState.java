package com.example.cscb07.ui.state;

import org.jetbrains.annotations.NotNull;

public class TimeUiState {
    public final int hour;
    public final int minute;

    public TimeUiState(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    @NotNull
    @Override
    public String toString() {
        String formattedMinute;
        if (minute < 10) formattedMinute = "0" + minute;
        else formattedMinute = "" + minute;
        return hour + ":" + formattedMinute;
    }
}
