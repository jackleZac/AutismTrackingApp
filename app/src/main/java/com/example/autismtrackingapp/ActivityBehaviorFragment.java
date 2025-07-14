package com.example.autismtrackingapp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class ActivityBehaviorFragment extends Fragment {
    private EditText activityInput, behaviorInput;
    private Button saveButton;
    private DatabaseReference database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_behavior, container, false);
        activityInput = view.findViewById(R.id.activity_input);
        behaviorInput = view.findViewById(R.id.behavior_input);
        saveButton = view.findViewById(R.id.save_button);
        database = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("activity_behavior");

        saveButton.setOnClickListener(v -> {
            String activity = activityInput.getText().toString().trim();
            String behavior = behaviorInput.getText().toString().trim();
            if (activity.isEmpty() || behavior.isEmpty()) {
                Toast.makeText(getContext(), "Enter activity and behavior", Toast.LENGTH_SHORT).show();
                return;
            }
            String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
            String noteId = database.push().getKey();
            database.child(noteId).setValue(new ActivityNote(date, activity, behavior))
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getContext(), "Note saved", Toast.LENGTH_SHORT).show();
                        activityInput.setText("");
                        behaviorInput.setText("");
                    });
        });
        return view;
    }

    public static class ActivityNote {
        public String date, activity, behavior;
        public ActivityNote() {}
        public ActivityNote(String date, String activity, String behavior) {
            this.date = date;
            this.activity = activity;
            this.behavior = behavior;
        }
    }
}