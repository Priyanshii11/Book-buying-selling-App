package com.example.mainproject.Navigation.Myproifle;

import static com.google.firebase.appcheck.internal.util.Logger.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mainproject.Navigation.Sell.SellFragment;
import com.example.mainproject.Navigation.Sell.UploadSell;
import com.example.mainproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore firestore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getWindow().setNavigationBarColor(Color.parseColor("#ffffff"));
        getWindow().setStatusBarColor(Color.parseColor("#3992AA"));


        EditText address = findViewById(R.id.address);
        EditText fullname= findViewById(R.id.fullname);
        EditText email = findViewById(R.id.email);
        EditText phonenum =findViewById(R.id.phone);
        EditText state = findViewById(R.id.state);



        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userID =auth.getCurrentUser().getUid();


        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView Save = findViewById(R.id.Saveb);

        DocumentReference documentReference = firestore.collection("Users").document(userID);
        documentReference.addSnapshotListener((documentSnapshot, error) -> {
            if (error != null) {
                Log.e("MyProfile", "Error fetching document", error);

            }

            if (documentSnapshot != null && documentSnapshot.exists()) {
                // Document exists, fetch data
                phonenum.setText(documentSnapshot.getString("Phonenum"));
                fullname.setText(documentSnapshot.getString("fname"));
                email.setText(documentSnapshot.getString("Email"));
                state.setText(documentSnapshot.getString("State"));
                address.setText(documentSnapshot.getString("Address"));


            } else {
                Log.d("MyProfile", "Document does not exist");
            }
        });

    Save.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String phone = phonenum.getText().toString();
            String fullName = fullname.getText().toString();
            String Email = email.getText().toString();
            String statee = state.getText().toString();
            String adddress = address.getText().toString();
            try{
            DocumentReference userRef = firestore.collection("Users").document(userID);
            userRef.update(
                            "fname", fullName,
                            "Email", Email ,
                            "Phonenum", phone,
                            "Address", adddress,
                            "State", statee
                    )
                    .addOnSuccessListener(aVoid -> {
                        // Optionally, you can finish() the activity to return to the previous screen
                        Toast.makeText(EditProfile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditProfile.this, Mypro.class);
                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(EditProfile.this, "Failed to update profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditProfile.this, Mypro.class);
                        startActivity(intent);
                        finish();
                    });
            }catch (Exception e){
                Log.e(TAG, "Error fetching Users data: " + e.getMessage());

        }
    }
    });
}

}