package com.example.autismtrackingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MedicationViewHolder> {
    private List<MedicationsActivity.Medication> medicationList;

    public MedicationAdapter(List<MedicationsActivity.Medication> medicationList) {
        this.medicationList = medicationList;
    }

    @Override
    public MedicationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medication, parent, false);
        return new MedicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MedicationViewHolder holder, int position) {
        MedicationsActivity.Medication medication = medicationList.get(position);
        holder.nameTextView.setText(medication.name);
        holder.dosageTextView.setText(medication.dosage);
        holder.scheduleTextView.setText(medication.schedule != null ? medication.schedule : "No schedule");
    }

    @Override
    public int getItemCount() {
        return medicationList.size();
    }

    static class MedicationViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, dosageTextView, scheduleTextView;

        MedicationViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.medication_name);
            dosageTextView = itemView.findViewById(R.id.medication_dosage);
            scheduleTextView = itemView.findViewById(R.id.medication_schedule);
        }
    }
}