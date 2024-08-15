package com.example.mainproject.Navigation.Rent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mainproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EditRentItems extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private Uri uri;
    private ImageView imageView;
    private String key;
    private String oldImageUrl;
    String userID;

    TextView title1, desc, Oprice, rentprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_rent_items);

        firestore = FirebaseFirestore.getInstance();

        imageView = findViewById(R.id.upimage);
        title1 = findViewById(R.id.uptitle);
        desc = findViewById(R.id.updesc);
        Oprice = findViewById(R.id.upoprice);
        rentprice = findViewById(R.id.upsprice);
        imageView = findViewById(R.id.upimage);

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            imageView.setImageURI(uri);
                        } else {
                            Toast.makeText(EditRentItems.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            key = bundle.getString("id");
            Glide.with(EditRentItems.this).load(bundle.getString("photoUrl")).into(imageView);
            title1.setText(bundle.getString("name"));
            desc.setText(bundle.getString("description"));
            Oprice.setText(bundle.getString("OriginalPrice"));
            rentprice.setText(bundle.getString("RentPrice"));
            oldImageUrl = bundle.getString("photoUrl");
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photopicker = new Intent(Intent.ACTION_PICK);
                photopicker.setType("image/*");
                activityResultLauncher.launch(photopicker);
            }
        });

        TextView save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    private void saveData() {


        if (uri == null) {
            // No image selected, show error message
            Toast.makeText(EditRentItems.this, "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Rent-book_images")
                .child(uri.getLastPathSegment());



        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri urlImage) {
                        // Image uploaded successfully, now update data in Firestore
                        updateData(urlImage.toString());

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        // Image upload failed, show error message
                        Toast.makeText(EditRentItems.this, "Error uploading image", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void updateData(String imageUrl) {

        String title = title1.getText().toString();
        String desc = title1.getText().toString();
        String oprice = title1.getText().toString();
        String rprice = title1.getText().toString();




        DocumentReference documentReference = firestore.collection("Users").document(userID)
                .collection("Rentals").document(key);
        documentReference.update("photoUrl", imageUrl)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // Image URL updated successfully, now delete old image from storage
                        StorageReference reference = FirebaseStorage.getInstance().getReference(oldImageUrl);
                        reference.delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(EditRentItems.this, "Item updated successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(EditRentItems.this, "Error deleting old image", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(EditRentItems.this, "Error updating data", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
    }
