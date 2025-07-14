package com.example.autismtrackingapp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DietaryListFragment extends Fragment {
    private RecyclerView recyclerView;
    private DietaryAdapter adapter;
    private List<DietaryEntry> entries = new ArrayList<>();
    private DatabaseReference database;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DietaryAdapter(entries);
        recyclerView.setAdapter(adapter);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("dietary_entries");

        EditText mealInput = view.findViewById(R.id.meal_input);
        EditText preferenceInput = view.findViewById(R.id.preference_input);
        EditText notesInput = view.findViewById(R.id.notes_input);
        Button saveButton = view.findViewById(R.id.save_button);

        saveButton.setOnClickListener(v -> {
            String meal = mealInput.getText().toString().trim();
            String preference = preferenceInput.getText().toString().trim();
            String notes = notesInput.getText().toString().trim();
            if (meal.isEmpty() || preference.isEmpty() || notes.isEmpty()) {
                Toast.makeText(getContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String entryId = database.push().getKey();
            database.child(entryId).setValue(new DietaryEntry(date, meal, preference, notes))
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getContext(), "Entry saved", Toast.LENGTH_SHORT).show();
                        mealInput.setText("");
                        preferenceInput.setText("");
                        notesInput.setText("");
                    });
        });

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                entries.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    DietaryEntry entry = data.getValue(DietaryEntry.class);
                    entries.add(entry);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
