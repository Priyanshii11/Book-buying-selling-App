package com.example.mainproject.Navigation.Rent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mainproject.R;

import java.util.List;

public class RentAdap extends RecyclerView.Adapter<RentAdap.RentViewHolder> {
    private List<RentItems> rentItemList;

    Context context;




    public RentAdap(Context context, List<RentItems> itemList) {
        this.rentItemList = itemList;
        this.context = context;

    }




    @NonNull
    @Override
    public RentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recylerview, parent, false);
        return new RentAdap.RentViewHolder(itemView);
    }



    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull RentViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textTitle.setText(rentItemList.get(position).getTitle());
        holder.textPrice.setText(String.valueOf(rentItemList.get(position).getPrice()));
        holder.textdesc.setText(rentItemList.get(position).getDescription());
        holder.textoPrice.setText(rentItemList.get(position).getOriginal_pricee());

        // Load image using Glide
        Glide.with(context)
                .load(rentItemList.get(position).getImage())
                .placeholder(R.drawable.baseline_add_a_photo_24) // Placeholder image while loading
                .error(R.drawable.booksell) // Image to display in case of error
                .into(holder.imageView);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pass the document ID of the clicked item to the detailed activity
                Intent intent = new Intent(context, DetailedRent.class);
                intent.putExtra("name", rentItemList.get(position).getTitle());
                intent.putExtra("description", rentItemList.get(position).getDescription());
                intent.putExtra("RentPrice", rentItemList.get(position).getPrice());
                intent.putExtra("OriginalPrice", rentItemList.get(position).getOriginal_pricee());
                intent.putExtra("photoUrl" , rentItemList.get(position).getImage());
                intent.putExtra("id" , rentItemList.get(holder.getAdapterPosition()).getKey());
                context.startActivity(intent);

            }
            });
        }


            @Override
            public int getItemCount() {
                return rentItemList.size();
            }

            static class RentViewHolder extends RecyclerView.ViewHolder {
                TextView textTitle, textPrice, textdesc, textoPrice;

                ImageView imageView;
                CardView cardView;
                // Add ImageView and other views as needed


                public RentViewHolder(@NonNull View itemView) {
                    super(itemView);

                    textTitle = itemView.findViewById(R.id.recTitle);
                    textPrice = itemView.findViewById(R.id.recprice);
                    textdesc = itemView.findViewById(R.id.desc);
                    textoPrice = itemView.findViewById(R.id.opricee);
                    imageView = itemView.findViewById(R.id.recImage);
                    cardView = itemView.findViewById(R.id.recCard);

                    // Initialize other views here
                }

            }

        }

