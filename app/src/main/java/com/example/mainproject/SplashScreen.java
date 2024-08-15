package com.example.mainproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mainproject.Navigation.Home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

FirebaseAuth mAuth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setStatusBarColor(Color.parseColor("#FF3992AA"));
        getWindow().setNavigationBarColor(Color.parseColor("#FF3992AA"));


        ImageView imageView = findViewById(R.id.photu);
        Animation move = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.anim);

        imageView.startAnimation(move);



    }
}



