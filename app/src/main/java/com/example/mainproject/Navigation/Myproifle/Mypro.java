package com.example.mainproject.Navigation.Myproifle;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.mainproject.Login;
import com.example.mainproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Mypro extends Fragment {

    FirebaseAuth auth;
    FirebaseFirestore firestore;
    String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mypro, container, false);

        TextView editProfile = view.findViewById(R.id.Edit);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editprofile();

            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView logout = view.findViewById(R.id.logout);
        TextView fullname= view.findViewById(R.id.fullname);
        TextView email = view.findViewById(R.id.email);
        TextView phonenum = view.findViewById(R.id.phone);
        TextView address = view.findViewById(R.id.address);
        TextView state = view.findViewById(R.id.state);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userID =auth.getCurrentUser().getUid();

        // Assuming "users" is the collection name and userID is the document ID
        DocumentReference documentReference = firestore.collection("Users").document(userID);
        documentReference.addSnapshotListener((documentSnapshot, error) -> {
            if (error != null) {
                Log.e("MyProfile", "Error fetching document", error);
                return;
            }

            if (documentSnapshot != null && documentSnapshot.exists()) {
                // Document exists, fetch data
                phonenum.setText(documentSnapshot.getString("Phonenum"));
                fullname.setText(documentSnapshot.getString("fname"));
                email.setText(documentSnapshot.getString("Email"));
                address.setText(documentSnapshot.getString("Address"));
                state.setText(documentSnapshot.getString("State"));

            } else {
                Log.d("MyProfile", "Document does not exist");
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();

            }
        });

        return view;
    }

    private void editprofile() {
        Intent intent = new Intent(getActivity(), EditProfile.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }
    }
