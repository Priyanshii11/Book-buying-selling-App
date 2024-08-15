package com.example.mainproject.Navigation.Sell;

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
import com.example.mainproject.Navigation.Rent.DetailedRent;
import com.example.mainproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SellAdapter extends RecyclerView.Adapter<SellAdapter.SellViewHolder> {
    private List<SellItem> sellItemList;
    Context context;

    public SellAdapter(Context context,List<SellItem> sellItemList) {
        this.sellItemList = sellItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public SellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recylerview, parent, false);
        return new SellViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SellViewHolder holder, int position) {
        SellItem sellItem = sellItemList.get(position);
        holder.textTitle.setText(sellItem.getTitle());
        holder.textPrice.setText(String.valueOf(sellItem.getPrice()));
       //Picasso.get().load(sellItem.getImage()).into(holder.imageView);


        // Load image using Glide
        Glide.with(holder.itemView.getContext())
                .load(sellItem.getImage())
                .placeholder(R.drawable.baseline_add_a_photo_24) // Placeholder image while loading
                .error(R.drawable.booksell) // Image to display in case of error
                .into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass the document ID of the clicked item to the detailed activity
                Intent intent = new Intent(context, DetailSell.class);
                intent.putExtra("name", sellItem.getTitle());
                intent.putExtra("photoUrl" , sellItem.getImage());
                intent.putExtra("OriginalPrice", sellItem.getOriginal_pricee());
                intent.putExtra("SellingPrice", sellItem.getPrice());
                intent.putExtra("description", sellItem.getDescription());
                intent.putExtra("id" , sellItem.getKey());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return sellItemList.size();
    }

    static class SellViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textPrice,textdesc,textoPrice;

        ImageView imageView;

        CardView cardView;
        // Add ImageView and other views as needed

        SellViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.recTitle);
            textPrice = itemView.findViewById(R.id.recprice);
            imageView = itemView.findViewById(R.id.recImage);
            textdesc = itemView.findViewById(R.id.desc);
            textoPrice = itemView.findViewById(R.id.opricee);
            imageView = itemView.findViewById(R.id.recImage);
            cardView = itemView.findViewById(R.id.recCard);
            // Initialize other views here
        }
    }
}
