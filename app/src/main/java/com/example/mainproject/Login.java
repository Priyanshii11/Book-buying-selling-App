package com.example.mainproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    FirebaseAuth auth;
    EditText Lemail ,password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setNavigationBarColor(Color.parseColor("#ffffff"));
        getWindow().setStatusBarColor(Color.parseColor("#3992AA"));


        Lemail = findViewById(R.id.LoginEmail);
        Button Lbtn = findViewById(R.id.loginbtn);
        TextView signback = findViewById(R.id.go2sign);
        password = findViewById(R.id.passwordEditText);


        signback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
                finish();
            }
        });

        auth= FirebaseAuth.getInstance();
        Lbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();

            }
        });


    }


    private void login() {
        String email = Lemail.getText().toString();
        String password1 = password.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(Login.this, "Please enter your email", Toast.LENGTH_SHORT).show();
        }

        if (password1.isEmpty()) {
            Toast.makeText(Login.this, "Please enter your password", Toast.LENGTH_SHORT).show();

        }else {
            ProgressDialog progressDialog = new ProgressDialog(Login.this);
            progressDialog.setMessage("Loading!!");
            progressDialog.setCancelable(false);
            progressDialog.show();

            auth.signInWithEmailAndPassword(email, password1)
                    .addOnCompleteListener(Login.this, task -> {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                Intent i = new Intent(Login.this, Dashboard.class);
                                i.putExtra("Uid", user.getUid());
                                startActivity(i);
                                finish();
                            }
                        } else {
                            Toast.makeText(Login.this, "Error in Login", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}