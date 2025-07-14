package com.example.autismtrackingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MedicalRecordsFragment extends Fragment {
    private EditText doctorName;
    private FirebaseAuth mAuth;
    private DatabaseReference database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medical_records, container, false);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("users")
                .child(mAuth.getCurrentUser().getUid()).child("medical_records");

        doctorName = view.findViewById(R.id.doctor_name);
        view.findViewById(R.id.save_button).setOnClickListener(v -> {
            String doctor = doctorName.getText().toString().trim();
            if (!doctor.isEmpty()) {
                database.child("record1").setValue(new MedicalRecord("2025-07-11", doctor, "Checkup"));
                Toast.makeText(getContext(), "Record saved", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}