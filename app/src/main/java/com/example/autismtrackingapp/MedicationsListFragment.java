package com.example.autismtrackingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MedicationsListFragment extends Fragment {
    private RecyclerView recyclerView;
    private MedicationAdapter adapter;
    private List<MedicationsActivity.Medication> medicationList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_medications_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.medications_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        medicationList = new ArrayList<>();
        adapter = new MedicationAdapter(medicationList);
        recyclerView.setAdapter(adapter);

        // Fetch medications from Firebase
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid).child("medications");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                medicationList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MedicationsActivity.Medication medication = snapshot.getValue(MedicationsActivity.Medication.class);
                    if (medication != null) {
                        medicationList.add(medication);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error (e.g., show Toast)
            }
        });
    }
}