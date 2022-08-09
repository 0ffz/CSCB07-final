package com.example.cscb07.ui.elements.screens.eventlist;

import androidx.recyclerview.widget.DiffUtil;

public class EventDiffCallback extends DiffUtil.Callback {
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }

    @Override
    public int getOldListSize() {
        return 0;
    }

    @Override
    public int getNewListSize() {
        return 0;
    }
}
