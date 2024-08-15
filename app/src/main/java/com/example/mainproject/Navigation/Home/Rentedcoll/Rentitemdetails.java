package com.example.mainproject.Navigation.Home.Rentedcoll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mainproject.Navigation.Home.Buy.Detailbuy;
import com.example.mainproject.Navigation.Home.HomeFragment;
import com.example.mainproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Rentitemdetails extends AppCompatActivity {
    private TextView title1, desc, oprice, sellprice;
    ImageView image1;
    ProgressBar progress;
    FirebaseFirestore db;
    String key;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentitemdetails);

        title1 = findViewById(R.id.Btitle);
        desc = findViewById(R.id.BDes);
        oprice = findViewById(R.id.BPrice);
        sellprice = findViewById(R.id.BSprice);
        image1 = findViewById(R.id.Bimage);


        auth = FirebaseAuth.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String title = bundle.getString("name");
            String description = bundle.getString("description");
            String price = bundle.getString("RentPrice");
            String originalPrice = bundle.getString("OriginaPrice");
            String image = bundle.getString("photoUrl");
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

                placeOrder(bookName,description,price,sell,phto);
            }
        });
    }

    private void placeOrder(String bookName, String description, String price, String sell, String phto) {

        String currentUserUid = auth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();

        // Create a map to represent book data
        Map<String, Object> Data = new HashMap<>();
        Data.put("Name", bookName);
        Data.put("Description", description);
        Data.put("Oprice", sell);
        Data.put("RP", price);
        Data.put("IMAGE" , phto);

        db.collection("Users").document(currentUserUid).collection("BuyItems").add(Data)
                .addOnSuccessListener(documentReference -> {
                    // Order placed successfully
                    Toast.makeText(Rentitemdetails.this, "Item Rented successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), RentedItems.class));

                })
                .addOnFailureListener(e -> {
                    // Error occurred while placing order
                    Toast.makeText(Rentitemdetails.this, "Failed to place order", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),RentedItems.class));

                });
    }
}