package com.example.navigation_view;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder> {

    ArrayList<FeaturedHelperClass> featuredList;

    public FeaturedAdapter(ArrayList<FeaturedHelperClass> featuredList) {
        this.featuredList = featuredList;
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        FeaturedViewHolder featuredViewHolder = new FeaturedViewHolder(view);
        return featuredViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedAdapter.FeaturedViewHolder holder, int position) {
        FeaturedHelperClass featuredHelperClass = featuredList.get(position);

        holder.image.setImageResource(featuredHelperClass.getImage());
        holder.product_name.setText(featuredHelperClass.getName());
        holder.price.setText(featuredHelperClass.getPrice());
    }

    @Override
    public int getItemCount() {
        return featuredList.size();
    }


    public static class FeaturedViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView product_name, price;

        public FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.featured_image);
            product_name = itemView.findViewById(R.id.featured_p_name);
            price = itemView.findViewById(R.id.featured_price);

        }
    }
}
