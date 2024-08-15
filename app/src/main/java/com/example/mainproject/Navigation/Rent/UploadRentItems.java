package com.example.mainproject.Navigation.Rent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mainproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;

public class UploadRentItems extends AppCompatActivity {
    ImageView imageView;
    Button uploadbtn;
    EditText bookname, bookdesc, bookprice, rentprice;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    private Uri imageUri;

    ProgressBar progressBar;

    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    final private StorageReference storageReference = FirebaseStorage.getInstance().getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_rent_items);

        getWindow().setNavigationBarColor(Color.parseColor("#ffffff"));
        getWindow().setStatusBarColor(Color.parseColor("#3992AA"));


        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView bac2rent = findViewById(R.id.back2rent);
        bac2rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadRentItems.this, RentFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });


        imageView = findViewById(R.id.uploadimg);
        bookname = findViewById(R.id.bookname);
        bookdesc = findViewById(R.id.bookdesc);
        rentprice = findViewById(R.id.bookprice);
        bookprice = findViewById(R.id.obookprice);
        progressBar =findViewById(R.id.progress);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            imageUri = data.getData();
                            imageView.setImageURI(imageUri);
                        } else {
                            Toast.makeText(UploadRentItems.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photopicker = new Intent();
                photopicker.setAction(Intent.ACTION_GET_CONTENT);
                photopicker.setType("image/*");
                activityResultLauncher.launch(photopicker);
            }
        });

        uploadbtn = findViewById(R.id.rentbtn);
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get user ID
                String userId = auth.getCurrentUser().getUid();
                String bookName = bookname.getText().toString();
                String description = bookdesc.getText().toString();
                String price = String.valueOf(Double.valueOf(bookprice.getText().toString()));
                String Rent = String.valueOf(Double.valueOf(rentprice.getText().toString()));


                if (TextUtils.isEmpty(bookName) || TextUtils.isEmpty(description) ||
                        TextUtils.isEmpty(price) || TextUtils.isEmpty(Rent)) {
                    // Show an error message or handle the case where fields are empty
                    Toast.makeText(UploadRentItems.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }


                //Create a map to represent book data
                Map<String, Object> rentData = new HashMap<>();
                rentData.put("name", bookName);
                rentData.put("description", description);
                rentData.put("RentPrice", Rent);
                rentData.put("OriginalPrice", price);
                rentData.put("photoUrl", imageUri);
                firestore.collection("Users").document(userId).collection("Rentals").add(rentData);


                if (imageUri != null) {
                    uploadtostorage(imageUri);

                } else {
                    Toast.makeText(UploadRentItems.this, "Please Select Image", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void uploadtostorage(Uri uri) {

        StorageReference imageRerfence = storageReference.child("Rent-book_images").child(auth.getCurrentUser().getUid()).child(uri.getLastPathSegment());
        imageRerfence.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                RentItems  dataclass = new RentItems();
                String key = databaseReference.push().getKey();
                databaseReference.child(key).setValue(dataclass);
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(UploadRentItems.this, "Uploaded", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UploadRentItems.this, RentFragment.class);
                startActivity(intent);
                finish();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(UploadRentItems.this,"Failed" , Toast.LENGTH_SHORT).show();
            }
        });
    }
    }




