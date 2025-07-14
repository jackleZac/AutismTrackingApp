package com.example.autismtrackingapp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseInitializer {
    public static void initializeData() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("users").child(uid);

        // 5 Dietary Entries
        DatabaseReference dietaryRef = database.child("dietary_entries");
        for (int i = 1; i <= 5; i++) {
            dietaryRef.child("entry" + i).setValue(new DietaryEntry("2025-07-0" + i, "Meal " + i, "Liked", "Healthy"));
        }

        // 5 Medical Records
        DatabaseReference medicalRef = database.child("medical_records");
        for (int i = 1; i <= 5; i++) {
            medicalRef.child("record" + i).setValue(new MedicalRecord("2025-07-0" + i, "Dr. Smith " + i, "Checkup " + i));
        }

        // 5 Activity Notes
        DatabaseReference activityRef = database.child("activity_behavior");
        for (int i = 1; i <= 5; i++) {
            activityRef.child("note" + i).setValue(new ActivityBehaviorFragment.ActivityNote("2025-07-0" + i, "Activity " + i, "Behavior " + i));
        }
    }
}