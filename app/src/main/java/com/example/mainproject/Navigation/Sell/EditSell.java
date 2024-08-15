package com.example.mainproject.Navigation.Sell;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.Edits;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mainproject.Navigation.Rent.EditRentItems;
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

import java.net.URI;

public class EditSell extends AppCompatActivity {

    TextView title1, desc, Oprice, rentprice ;
    ImageView imageView;

    String userID , key ,oldImageUrl;
    private FirebaseFirestore firestore;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri uri;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sell);

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firestore = FirebaseFirestore.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference();

        imageView = findViewById(R.id.upimage);
        title1 = findViewById(R.id.uptitle);
        desc = findViewById(R.id.updesc);
        Oprice = findViewById(R.id.upoprice);
        rentprice = findViewById(R.id.upsprice);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            key = bundle.getString("id");
            Glide.with(EditSell.this).load(bundle.getString("photoUrl")).into(imageView);
            title1.setText(bundle.getString("name"));
            desc.setText(bundle.getString("description"));
            Oprice.setText(bundle.getString("OriginalPrice"));
            rentprice.setText(bundle.getString("SellingPrice"));
            oldImageUrl = bundle.getString("photoUrl");
        }






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
                            Toast.makeText(EditSell.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

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
            Toast.makeText(EditSell.this, "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Sell-book_images")
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
                        Toast.makeText(EditSell.this, "Error uploading image", Toast.LENGTH_SHORT).show();
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
                .collection("Sells").document(key);
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
                                        Toast.makeText(EditSell.this, "Item updated successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(EditSell.this, "Error deleting old image", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(EditSell.this, "Error updating data", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
    }



