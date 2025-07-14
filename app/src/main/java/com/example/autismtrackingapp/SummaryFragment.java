package com.example.autismtrackingapp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class SummaryFragment extends Fragment {
    private TextView summaryText;
    private Button shareButton;
    private DatabaseReference database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        summaryText = view.findViewById(R.id.summary_text);
        shareButton = view.findViewById(R.id.share_button);
        database = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StringBuilder summary = new StringBuilder("Health Summary:\n\n");
                // Dietary Entries
                for (DataSnapshot entry : snapshot.child("dietary_entries").getChildren()) {
                    String meal = entry.child("meal").getValue(String.class);
                    String date = entry.child("date").getValue(String.class);
                    if (meal != null && date != null) {
                        summary.append("Diet: ").append(meal).append(" (").append(date).append(")\n");
                    }
                }
                // Medical Records
                for (DataSnapshot record : snapshot.child("medical_records").getChildren()) {
                    String reason = record.child("reason").getValue(String.class);
                    String date = record.child("date").getValue(String.class);
                    if (reason != null && date != null) {
                        summary.append("Medical: ").append(reason).append(" (").append(date).append(")\n");
                    }
                }
                // Activity Notes
                for (DataSnapshot note : snapshot.child("activity_behavior").getChildren()) {
                    String activity = note.child("activity").getValue(String.class);
                    String date = note.child("date").getValue(String.class);
                    if (activity != null && date != null) {
                        summary.append("Activity: ").append(activity).append(" (").append(date).append(")\n");
                    }
                }
                summaryText.setText(summary.length() > "Health Summary:\n\n".length() ? summary.toString() : "No data available.");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        shareButton.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Autism Tracking Summary");
            shareIntent.putExtra(Intent.EXTRA_TEXT, summaryText.getText().toString());
            startActivity(Intent.createChooser(shareIntent, "Share Summary"));
        });
        return view;
    }
}