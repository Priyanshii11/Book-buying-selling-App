package com.example.mainproject.Navigation.Sell;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class SellFragment extends Fragment {

    private RecyclerView recyclerView;
    private SellAdapter Adapter;
    private List<SellItem> ItemList;

    FirebaseFirestore firestore;

    FirebaseAuth auth;



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View rootView = inflater.inflate(R.layout.fragment_sell_f_ragment, container, false);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        FloatingActionButton floatingActionButton = rootView.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UploadSell.class);
                startActivity(intent);
            }
        });
        recyclerView = rootView.findViewById(R.id.recycleviewl);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ItemList = new ArrayList<>();
        Adapter = new SellAdapter(getContext(),ItemList);
        recyclerView.setAdapter(Adapter);


        fetchSellDataFromFirestore(); // Method to fetch data from Firestore

        return rootView;

    }
    @SuppressLint("NotifyDataSetChanged")
    private void fetchSellDataFromFirestore() {

        String userId = auth.getCurrentUser().getUid();
        firestore = FirebaseFirestore.getInstance();

        // Fetch sell data from Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").document(userId)
                .collection("Sells")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        // Extract sell item data from documentSnapshot
                        String title = documentSnapshot.getString("name");
                        String image = documentSnapshot.getString("photoUrl");
                        String oprice = documentSnapshot.getString("OriginalPrice");
                        String price = documentSnapshot.getString("SellingPrice");
                        String des = documentSnapshot.getString("description");
//                        String imageUrl = "gs://recyclereads-98ed5.appspot.com/Sell-book_images" + image;

                        // Create SellItem object
                        SellItem Item = new SellItem(title, oprice,image,des , price);
                        ItemList.add(Item);
                    }
                    Adapter.notifyDataSetChanged(); // Notify adapter about data changes
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                });
    }

    }

