package com.example.mainproject.Navigation.Home;

import static com.google.firebase.appcheck.internal.util.Logger.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.mainproject.Navigation.Home.Buy.BuyAdap;
import com.example.mainproject.Navigation.Home.Buy.BuyItems;
import com.example.mainproject.Navigation.Sell.SellAdapter;
import com.example.mainproject.Navigation.Sell.SellItem;
import com.example.mainproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class MyOrder extends Fragment {


    private List<SellItem> Items;
    SellAdapter adapter;

    RecyclerView recyclerView1;

    ProgressBar progressBar;

    FirebaseFirestore firestore;

    FirebaseAuth auth;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_order, container, false);
        progressBar =view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


        recyclerView1 = view.findViewById(R.id.recycleview2);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext() ,1);
        recyclerView1.setLayoutManager(gridLayoutManager);
        Items = new ArrayList<>();
        adapter = new SellAdapter(getContext(),Items);
        recyclerView1.setAdapter(adapter);
        orderrecycleview();

        return view;
    }

    private void orderrecycleview() {

        try{
            auth=FirebaseAuth.getInstance();

            String userId = auth.getCurrentUser().getUid();
            firestore = FirebaseFirestore.getInstance();

            // Fetch sell data from Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Users").document(userId)
                    .collection("BuyItems")
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            // Extract sell item data from documentSnapshot
                            String title = documentSnapshot.getString("Name");
                            String price = documentSnapshot.getString("SP");
                            String image = documentSnapshot.getString("photoUrl");
                            String des = documentSnapshot.getString("Description");
                            String oprice = documentSnapshot.getString("OPrice");

                            // Create SellItem object
                            SellItem Item = new SellItem(title, price,image,des , oprice);
                            Items.add(Item);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                        adapter.notifyDataSetChanged(); // Notify adapter about data changes
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                        Log.e(TAG, "Error fetching Users data: " + e.getMessage());

                    });

        }catch (Exception e){
            Log.e(TAG, "Error fetching Users " + e.getMessage());



        }
    }

}


