package com.example.cscb07.ui.elements.screens;

import com.example.cscb07.data.models.VenueModel;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.cscb07.R;
import com.example.cscb07.ui.state.VenueUiState;

public class RecyclerAdapt extends RecyclerView.Adapter<RecyclerAdapt.ViewHolder>{

    private ArrayList<VenueUiState> localDataSet;
    private ItemClickListener clickListener;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView description;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            name = view.findViewById(R.id.venueTitle);
            description = view.findViewById(R.id.venueDescription);
        }

    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet ArrayList<VenueUiState> containing the data to populate views to be used
     * by RecyclerView.
     */
    public RecyclerAdapt(ArrayList<VenueUiState> dataSet, ItemClickListener clickListener) {
        localDataSet = dataSet;
        this.clickListener  = clickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.venuecard, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.name.setText(localDataSet.get(position).getName());
        viewHolder.description.setText(localDataSet.get(position).getDescription());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(localDataSet.get(position));
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
    public interface ItemClickListener {
        public void onItemClick(VenueUiState dataModel);
    }
}