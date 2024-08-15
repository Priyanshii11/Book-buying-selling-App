package com.example.mainproject.Navigation.Home.Buy;

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


public class BuyAdap extends RecyclerView.Adapter<BuyAdap.BuyViewHolder> {

    Context context;

    List<BuyItems> buyItemsList;
    public BuyAdap(Context context, List<BuyItems> ItemList) {
        this.buyItemsList = ItemList;
        this.context = context;

    }


    @NonNull
    @Override
    public BuyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.buyview
                          , parent, false);
        return new BuyAdap.BuyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull BuyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.Title.setText(buyItemsList.get(position).getTitle());
        holder.SPrice.setText(buyItemsList.get(position).getSPrice());
        holder.Dessc.setText(buyItemsList.get(position).getDessc());
        holder.OPrice.setText(buyItemsList.get(position).getOPrice());

        // Load image using Glide
        Glide.with(context)
                .load(buyItemsList.get(position).getImage())
                .placeholder(R.drawable.baseline_add_a_photo_24) // Placeholder image while loading
                .error(R.drawable.booksell) // Image to display in case of error
                .into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(context, Detailbuy.class);
                intent.putExtra("id" , buyItemsList.get(holder.getAdapterPosition()).getKey());
                intent.putExtra("Name", buyItemsList.get(position).getTitle());
                intent.putExtra("Description", buyItemsList.get(position).getDessc());
                intent.putExtra("SPrice", buyItemsList.get(position).getSPrice());
                intent.putExtra("OPrice", buyItemsList.get(position).getOPrice());
                intent.putExtra("PhotoUrl" , buyItemsList.get(position).getImage());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (buyItemsList != null) {
            return buyItemsList.size(); // Check for null before accessing size
        } else {
            return 0; // Return 0 if list is null
        }
    }

    static class BuyViewHolder extends RecyclerView.ViewHolder {
        TextView Title, SPrice , Dessc , OPrice;

        ImageView imageView;
        CardView cardView;

        public BuyViewHolder(@NonNull View itemView) {
            super(itemView);

            Title = itemView.findViewById(R.id.rec2Title);
            SPrice = itemView.findViewById(R.id.SPrice);
            Dessc = itemView.findViewById(R.id.recDesc);
            OPrice = itemView.findViewById(R.id.rec0price);
            imageView = itemView.findViewById(R.id.rec2Image);
            cardView = itemView.findViewById(R.id.rec2card);
        }
    }
}

