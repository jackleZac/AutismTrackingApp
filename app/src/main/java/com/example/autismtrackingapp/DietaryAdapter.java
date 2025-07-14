package com.example.autismtrackingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DietaryAdapter extends RecyclerView.Adapter<DietaryAdapter.ViewHolder> {
    private List<DietaryEntry> entries; // Updated

    public DietaryAdapter(List<DietaryEntry> entries) { // Updated
        this.entries = entries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dietary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DietaryEntry entry = entries.get(position); // Updated
        holder.mealText.setText(entry.meal + " (" + entry.preference + ")"); // Check if 'preference' exists
        holder.dateText.setText(entry.date);
        holder.notesText.setText(entry.notes); // Check if 'notes' exists
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mealText, dateText, notesText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealText = itemView.findViewById(R.id.meal_text);
            dateText = itemView.findViewById(R.id.date_text);
            notesText = itemView.findViewById(R.id.notes_text);
        }
    }
}