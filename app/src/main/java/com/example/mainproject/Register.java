package com.example.mainproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
public class Register extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    DatabaseReference databaseReference;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setNavigationBarColor(Color.parseColor("#ffffff"));
        getWindow().setStatusBarColor(Color.parseColor("#3992AA"));
        EditText Sname = findViewById(R.id.SignName);
        EditText Semail = findViewById(R.id.SignEmail);
        EditText Spass = findViewById(R.id.SignPass);
        EditText SPhone = findViewById(R.id.Phonenum);
        Button signbtn = findViewById(R.id.signupbtn);
        TextView loginback = findViewById(R.id.back2login);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        loginback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = Sname.getText().toString().trim();
                String em = Semail.getText().toString().trim();
                String lpass = Spass.getText().toString().trim();
                String phoneNum = SPhone.getText().toString().trim();

                if (fname.isEmpty()) {
                    Sname.setError("Name is required");
                    return;
                }

                if (em.isEmpty()) {
                    Semail.setError("Email is required");
                    return;
                }

                if (lpass.isEmpty()) {
                    Spass.setError("Password is required");
                    return;
                }

                if (phoneNum.isEmpty()) {
                    SPhone.setError("Phone number is required");
                    return;
                }

                ProgressDialog progressDialog = new ProgressDialog(Register.this);
                progressDialog.setMessage("Loading!!");
                progressDialog.setCancelable(false);
                progressDialog.show();

                auth.createUserWithEmailAndPassword(em, lpass)
                        .addOnCompleteListener(Register.this, task -> {
                            if (task.isSuccessful()) {
                                userID = auth.getCurrentUser().getUid();
                                DocumentReference documentReference = firestore.collection("Users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("fname", fname);
                                user.put("Email", em);
                                user.put("password", lpass);
                                user.put("Phonenum", phoneNum);
                                user.put("Uid" ,userID);

                                documentReference.set(user).addOnSuccessListener(aVoid -> {
                                    Log.d("MyProfile", "User Created" + userID);
                                });
                                Intent intent = new Intent(Register.this, Dashboard.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Register.this, "Operation failed", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        });
            }
        });

    }
}