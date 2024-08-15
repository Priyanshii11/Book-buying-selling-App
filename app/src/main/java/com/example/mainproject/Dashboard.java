package com.example.mainproject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.mainproject.Navigation.Aboutus;
import com.example.mainproject.Navigation.Home.HomeFragment;
import com.example.mainproject.Navigation.Home.MyOrder;
import com.example.mainproject.Navigation.Feedback;
import com.example.mainproject.Navigation.Home.Rentedcoll.RentedItems;
import com.example.mainproject.Navigation.Myproifle.Mypro;
import com.example.mainproject.Navigation.Rent.RentFragment;
import com.example.mainproject.Navigation.Sell.SellFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

     NavigationView navigationView;

     DrawerLayout drawerLayout;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getWindow().setNavigationBarColor(Color.parseColor("#3992AA"));
        getWindow().setStatusBarColor(Color.parseColor("#3992AA"));

        drawerLayout = findViewById(R.id.drawer_layout);
         Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav,R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.home);

        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                            new HomeFragment()).commit();
                    getinfo();
                } else if (id == R.id.myorder) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                            new MyOrder()).commit();

                } else if (id == R.id.myprofile) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                            new Mypro()).commit();

                }
                else if (id == R.id.sellbooks) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                            new SellFragment()).commit();
                }
                else if (id == R.id.RentBooks) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                            new RentFragment()).commit();
                }
                else if (id == R.id.RentedItems) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                            new RentedItems()).commit();
                }
                else if (id == R.id.aboutus) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                            new Aboutus()).commit();
                }
                else if (id == R.id.Feedback) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                            new Feedback()).commit();
                }
                else if (id==R.id.logout){
                    logout();

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
    private void getinfo() {
        Bundle bundle = new Bundle();
        String id  = bundle.getString("Uid");

        bundle.putString("Uid" , id);
    }
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        ProgressDialog progressDialog = new ProgressDialog(Dashboard.this);
        progressDialog.setMessage(" Loading! ");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Intent intent = new Intent( this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    }
