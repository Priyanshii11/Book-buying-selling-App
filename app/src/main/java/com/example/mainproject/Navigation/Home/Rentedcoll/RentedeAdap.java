package com.example.mainproject.Navigation.Home.Rentedcoll;

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
import com.example.mainproject.Navigation.Home.Buy.Detailbuy;
import com.example.mainproject.R;

import java.util.List;

public class RentedeAdap extends RecyclerView.Adapter<RentedeAdap.ViewHolder>{

    Context context;

    List<RentedClass> ItemsList;

    public RentedeAdap(Context context, List<RentedClass> ItemList) {
        this.ItemsList = ItemList;
        this.context = context;

    }


    @NonNull
    @Override
    public RentedeAdap.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.buyview
                        , parent, false);
        return new RentedeAdap.ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull RentedeAdap.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.Title.setText(ItemsList.get(position).getTitle());
        holder.SPrice.setText(ItemsList.get(position).getSprice());
        holder.Dessc.setText(ItemsList.get(position).getDesc());
        holder.OPrice.setText(ItemsList.get(position).getOprice());

        // Load image using Glide
        Glide.with(context)
                .load(ItemsList.get(position).getImage())
                .placeholder(R.drawable.baseline_add_a_photo_24) // Placeholder image while loading
                .error(R.drawable.booksell) // Image to display in case of error
                .into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(context, Rentitemdetails.class);
                intent.putExtra("name", ItemsList.get(position).getTitle());
                intent.putExtra("description", ItemsList.get(position).getDesc());
                intent.putExtra("RentPrice", ItemsList.get(position).getSprice());
                intent.putExtra("OriginaPrice", ItemsList.get(position).getOprice());
                intent.putExtra("photoUrl" ,ItemsList.get(position).getImage());
                intent.putExtra("id" , ItemsList.get(holder.getAdapterPosition()).getKey());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (ItemsList != null) {
            return ItemsList.size(); // Check for null before accessing size
        } else {
            return 0; // Return 0 if list is null
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Title, SPrice , Dessc , OPrice;

        ImageView imageView;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
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
