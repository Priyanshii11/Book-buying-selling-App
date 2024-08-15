package com.example.mainproject.Navigation.Home.Buy;


import static com.google.firebase.appcheck.internal.util.Logger.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mainproject.Navigation.Home.HomeFragment;
import com.example.mainproject.Navigation.Sell.SellFragment;
import com.example.mainproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
public class Detailbuy extends AppCompatActivity {


     private   TextView title1, desc, oprice, sellprice;
        ImageView image1;
        ProgressBar progress;
        FirebaseFirestore db;
        String key;
    FirebaseAuth auth;
        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detailbuy);

            title1 = findViewById(R.id.Btitle);
            desc = findViewById(R.id.BDes);
            oprice = findViewById(R.id.BPrice);
            sellprice = findViewById(R.id.BSprice);
            image1 = findViewById(R.id.Bimage);
            progress = findViewById(R.id.progress);


            auth = FirebaseAuth.getInstance();

            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                String title = bundle.getString("Name");
                String description = bundle.getString("Description");
                String price = bundle.getString("SPrice");
                String originalPrice = bundle.getString("OPrice");
                String image = bundle.getString("PhotoUrl");
                key = bundle.getString("id");

                Glide.with(this).load(image).into(image1);
                title1.setText(title);
                desc.setText(description);
                oprice.setText(originalPrice);
                sellprice.setText(price);
            }



            TextView placeorder = findViewById(R.id.placeorder);
            placeorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String bookName = title1.getText().toString();
                    String description = desc.getText().toString();
                    String price = oprice.getText().toString();
                    String sell = sellprice.getText().toString();
                    String phto = image1.toString();

//                    String Key = key.toString();
                    placeOrder(bookName,description,price,sell,phto);
                }
            });

        }

    private void placeOrder(String bookName, String description, String price, String sell, String phto) {

            try {
                String currentUserUid = auth.getInstance().getCurrentUser().getUid();
                db = FirebaseFirestore.getInstance();

                // Create a map to represent book data
                Map<String, Object> Data = new HashMap<>();
                Data.put("Name", bookName);
                Data.put("Description", description);
                Data.put("Oprice", sell);
                Data.put("SP", price);
                Data.put("IMAGE" , phto);

                db.collection("Users").document(currentUserUid).collection("BuyItems").add(Data)
                        .addOnSuccessListener(documentReference -> {
                            // Order placed successfully
                            Toast.makeText(Detailbuy.this, "Order placed successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeFragment.class));

                        })
                        .addOnFailureListener(e -> {
                            // Error occurred while placing order
                            Toast.makeText(Detailbuy.this, "Failed to place order", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeFragment.class));

                        });

            }catch (Exception e){
                Log.e(TAG, "Error fetching Users data: " + e.getMessage());


            }

    }
    }



