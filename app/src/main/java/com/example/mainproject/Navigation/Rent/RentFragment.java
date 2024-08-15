package com.example.mainproject.Navigation.Rent;

import static com.google.firebase.appcheck.internal.util.Logger.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class RentFragment extends Fragment {

    private RentAdap rentAdap;
    private List<RentItems> ItemList;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    CollectionReference collectionReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_rent, container, false);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        FloatingActionButton floatingActionButton = rootView.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UploadRentItems.class);
                startActivity(intent);

            }
        });
        RecyclerView recyclerView = rootView.findViewById(R.id.recycleview1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext() ,1);
        recyclerView.setLayoutManager(gridLayoutManager);

        ItemList = new ArrayList<>();
        rentAdap = new RentAdap(getContext(),ItemList);
        recyclerView.setAdapter(rentAdap);




        firestore = FirebaseFirestore.getInstance();
        String userId = auth.getCurrentUser().getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").document(userId)
                .collection("Rentals")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                String title = documentSnapshot.getString("name");
                String price = documentSnapshot.getString("RentPrice");
                String image = documentSnapshot.getString("photoUrl");
                String des = documentSnapshot.getString("description");
                String oprice = documentSnapshot.getString("OriginalPrice");


                RentItems rentItems;
                rentItems = new RentItems(title,price,image,oprice,des);
                rentItems.setKey(documentSnapshot.getId());
                ItemList.add(rentItems);
            }
            rentAdap.notifyDataSetChanged();
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error fetching data: " + e.getMessage());
        });


        return rootView;

    }


}



