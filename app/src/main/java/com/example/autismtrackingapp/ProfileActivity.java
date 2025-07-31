package com.example.autismtrackingapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {
    public static class Profile {
        public String name;
        public String identityNumber;
        public String emergencyContact;
        public String emergencyNumber;
        public String address;

        public Profile() {
            // Default constructor for Firebase
        }

        public Profile(String name, String identityNumber, String emergencyContact, String emergencyNumber, String address) {
            this.name = name;
            this.identityNumber = identityNumber;
            this.emergencyContact = emergencyContact;
            this.emergencyNumber = emergencyNumber;
            this.address = address;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Edit Profile");
        }

        EditText nameInput = findViewById(R.id.profile_name);
        EditText identityNumberInput = findViewById(R.id.identity_number);
        EditText emergencyContactInput = findViewById(R.id.emergency_contact);
        EditText emergencyNumberInput = findViewById(R.id.emergency_number);
        EditText addressInput = findViewById(R.id.address);
        Button saveButton = findViewById(R.id.save_button);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Log.e("ProfileActivity", "User not logged in");
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("users")
                .child(user.getUid())
                .child("profile");

        saveButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String identityNumber = identityNumberInput.getText().toString().trim();
            String emergencyContact = emergencyContactInput.getText().toString().trim();
            String emergencyNumber = emergencyNumberInput.getText().toString().trim();
            String address = addressInput.getText().toString().trim();
            if (name.isEmpty() || identityNumber.isEmpty()) {
                Log.w("ProfileActivity", "Name or identity number empty");
                Toast.makeText(this, "Enter name and identity number", Toast.LENGTH_SHORT).show();
                return;
            }
            Profile profile = new Profile(name, identityNumber, emergencyContact, emergencyNumber, address);
            Log.d("ProfileActivity", "Attempting to save profile: " + name + ", " + identityNumber);
            database.setValue(profile)
                    .addOnSuccessListener(aVoid -> {
                        Log.d("ProfileActivity", "Profile saved successfully: " + name + ", " + identityNumber);
                        Toast.makeText(this, "Profile saved", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Log.e("ProfileActivity", "Failed to save profile: " + e.getMessage());
                        Toast.makeText(this, "Error saving profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.d("ProfileActivity", "Back arrow clicked");
        finish();
        return true;
    }
}