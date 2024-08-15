package com.example.mainproject.Navigation.Rent;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mainproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailedRent extends AppCompatActivity {

  private   TextView title1 , desc , oprice , rentprice;

  private   ImageView image1 ;

    private FirebaseAuth auth ;
    private String userid;
    private String key = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_rent);

        getWindow().setNavigationBarColor(Color.parseColor("#3992AA"));
        getWindow().setStatusBarColor(Color.parseColor("#3992AA"));

        title1 = findViewById(R.id.detailtitle);
        desc = findViewById(R.id.detaildesc);
        oprice = findViewById(R.id.oprice);
        rentprice = findViewById(R.id.sprice);
        image1 = findViewById(R.id.detailimage);

        auth = FirebaseAuth.getInstance();
        userid = auth.getCurrentUser().getUid();



        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String title = bundle.getString("name");
            String image = bundle.getString("photoUrl");
            String originalPrice = bundle.getString("OriginalPrice");
            String price = bundle.getString("RentPrice");
            String description = bundle.getString("description");
            key = bundle.getString("id");

            Glide.with(this).load(image).into(image1);
            title1.setText(title);
            desc.setText(description);
            oprice.setText(originalPrice);
            rentprice.setText(price);
        }


//        TextView edit = findViewById(R.id.editrent);
//        edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1 = new Intent(DetailedRent.this , EditRentItems.class)
//                .putExtra("name" , title1.getText().toString())
//                .putExtra("description" , desc.getText().toString())
//                .putExtra("RentPrice" , rentprice.getText().toString())
//                .putExtra("OriginalPrice" , oprice.getText().toString())
//                .putExtra("photoUrl" , (CharSequence) image1)
//                        .putExtra("id" , key);
//                startActivity(intent1);
//                finish();
//            }
//        });

        TextView delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DocumentReference documentReference = FirebaseFirestore.getInstance()
                        .collection("Users")
                        .document(userid)
                        .collection("Rentals")
                        .document(key); // Assuming 'key' is the ID of the document to delete

                documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(DetailedRent.this, "Item Deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), RentFragment.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DetailedRent.this, "Error Deleting Item", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), RentFragment.class));
                        finish();
                    }
                });
            }
        });


    }

    }






