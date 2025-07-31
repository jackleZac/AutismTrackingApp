package com.example.autismtrackingapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileViewFragment extends Fragment {
    private TextView nameText, identityNumberText, emergencyContactText, emergencyNumberText, addressText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_view, container, false);

        nameText = view.findViewById(R.id.profile_name);
        identityNumberText = view.findViewById(R.id.identity_number);
        emergencyContactText = view.findViewById(R.id.emergency_contact);
        emergencyNumberText = view.findViewById(R.id.emergency_number);
        addressText = view.findViewById(R.id.address);
        Button editButton = view.findViewById(R.id.save_button);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Log.e("ProfileViewFragment", "User not logged in");
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigate(R.id.to_dietaryListFragment);
            return view;
        }

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("users")
                .child(user.getUid())
                .child("profile");

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ProfileActivity.Profile profile = snapshot.getValue(ProfileActivity.Profile.class);
                if (profile != null) {
                    nameText.setText(profile.name != null && !profile.name.isEmpty() ? profile.name : "N/A");
                    identityNumberText.setText(profile.identityNumber != null && !profile.identityNumber.isEmpty() ? profile.identityNumber : "N/A");
                    emergencyContactText.setText(profile.emergencyContact != null && !profile.emergencyContact.isEmpty() ? profile.emergencyContact : "N/A");
                    emergencyNumberText.setText(profile.emergencyNumber != null && !profile.emergencyNumber.isEmpty() ? profile.emergencyNumber : "N/A");
                    addressText.setText(profile.address != null && !profile.address.isEmpty() ? profile.address : "N/A");
                    Log.d("ProfileViewFragment", "Profile loaded: " + (profile.name != null ? profile.name : "null"));
                } else {
                    nameText.setText("N/A");
                    identityNumberText.setText("N/A");
                    emergencyContactText.setText("N/A");
                    emergencyNumberText.setText("N/A");
                    addressText.setText("N/A");
                    Log.d("ProfileViewFragment", "No profile data found");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("ProfileViewFragment", "Error loading profile: " + error.getMessage());
                Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        editButton.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.to_profileActivity));

        return view;
    }
}