package com.example.mainproject.Navigation;

import static com.google.firebase.appcheck.internal.util.Logger.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.example.mainproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class Feedback extends Fragment {

    TextView send;
    String userID;

    EditText userFeedback;

    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        send = view.findViewById(R.id.SEND);
        userFeedback = view.findViewById(R.id.feedback);



        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        userID =auth.getCurrentUser().getUid();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedbackId = UUID.randomUUID().toString();
                String feedback = userFeedback.getText().toString();

                Map<String, Object> feedbackData = new HashMap<>();
                feedbackData.put("content", feedback); // Replace "userFeedback" with the actual feedback content
                feedbackData.put("userId", userID); // Replace "userId" with the ID of the user submitting the feedback
                feedbackData.put("timestamp", ServerValue.TIMESTAMP); // Include timestamp for tracking when the feedback was submitted


                firebaseDatabase.getReference("Feedback").child(feedbackId)
                        .setValue(feedbackData)
                        .addOnSuccessListener(aVoid -> {
                            // Feedback successfully stored
                            Toast.makeText(getContext() , "Thanks for giving Your Feedback" , Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Thanks for submitting feedback" );

                        })
                        .addOnFailureListener(e -> {
                            // Error storing feedback
                            Toast.makeText(getContext() , "Thanks for giving Your Feedback" , Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Error submitting feedback", e);
                        });


            }
        });

        return view;
    }

    private void sendfeedback() {

    }
}