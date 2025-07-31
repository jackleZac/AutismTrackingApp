package com.example.autismtrackingapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MedicationsActivity extends AppCompatActivity {
    public static class Medication {
        public String name;
        public String dosage;
        public String schedule;

        public Medication() {
            // Default constructor for Firebase
        }

        public Medication(String name, String dosage, String schedule) {
            this.name = name;
            this.dosage = dosage;
            this.schedule = schedule;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications);

        // Set up custom Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Add Medication");
        }

        EditText nameInput = findViewById(R.id.medication_name);
        EditText dosageInput = findViewById(R.id.dosage);
        EditText scheduleInput = findViewById(R.id.schedule);
        Button saveButton = findViewById(R.id.save_button);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("medications");

        saveButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String dosage = dosageInput.getText().toString().trim();
            String schedule = scheduleInput.getText().toString().trim();
            if (name.isEmpty() || dosage.isEmpty()) {
                Toast.makeText(this, "Enter name and dosage", Toast.LENGTH_SHORT).show();
                return;
            }
            String entryId = database.push().getKey();
            database.child(entryId).setValue(new Medication(name, dosage, schedule))
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Medication saved", Toast.LENGTH_SHORT).show();
                        nameInput.setText("");
                        dosageInput.setText("");
                        scheduleInput.setText("");
                    });
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        android.util.Log.d("MedicationsActivity", "Back arrow clicked");
        finish(); // Close activity and return to previous fragment
        return true;
    }
}