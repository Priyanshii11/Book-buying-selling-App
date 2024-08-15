package com.example.mainproject.Navigation.Home;
import static com.google.firebase.appcheck.internal.util.Logger.TAG;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mainproject.Navigation.Home.Buy.BuyAdap;
import com.example.mainproject.Navigation.Home.Buy.BuyItems;
import com.example.mainproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;
public class HomeFragment extends Fragment {
    private List<BuyItems> buyItems;

    BuyAdap adapter2;

    RecyclerView recyclerView1;

    ProgressBar progressBar;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        progressBar =view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        // Display rentItem and sellItem data in RecyclerView using adapters
        recyclerView1 = view.findViewById(R.id.recyclebuy);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext() ,1);
        recyclerView1.setLayoutManager(gridLayoutManager1);
        buyItems = new ArrayList<>();
        adapter2 = new BuyAdap(getContext(),buyItems);
        recyclerView1.setAdapter(adapter2);
        buyrecycleview();
        return view;
    }
    private void buyrecycleview() {

        // Step 1: Get the UID of the current user
        String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Step 2: Get a reference to the Firestore database
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        // Step 3: Query Firestore to fetch documents from the "Users" collection excluding the current user
        CollectionReference usersRef = db.collection("Users");
        usersRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot userSnapshot : queryDocumentSnapshots) {
                        String userId = userSnapshot.getId();
                        // Step 4: Check if the ID is not equal to the current user's ID
                        if (!userId.equals(currentUserUid)) {
                            // Step 5: Fetch data from the user document

                            // Get a reference to the "Sells" subcollection of the current user
                            CollectionReference SellsRef = usersRef.document(userId).collection("Sells");
                            SellsRef.get()
                                    .addOnSuccessListener(QueryDocumentSnapshots -> {
                                        for (QueryDocumentSnapshot Doc : QueryDocumentSnapshots) {
                                            // Step 5: Extract details and create model object for Sells
                                            String title = Doc.getString("name");
                                            String price = Doc.getString("SellingPrice");
                                            String oprice = Doc.getString("OriginalPrice");
                                            String image = Doc.getString("photoUrl");
                                            String des = Doc.getString("description");


                                            // Step 6: Create BuyItems object and add it to the buyItems list
                                            BuyItems item;
                                            item = new BuyItems(title, price, image,des,oprice);
                                            buyItems.add(item);
                                            progressBar.setVisibility(View.INVISIBLE);
                                        }
                                        // Step 7: Notify adapter about the data change
                                        adapter2.notifyDataSetChanged();
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e(TAG, "Error fetching Users data: " + e.getMessage());

                                        }
                                    });
                        }

                    }
                });

    }

}


