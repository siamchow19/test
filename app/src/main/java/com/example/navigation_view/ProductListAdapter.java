package com.example.navigation_view;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {
    ArrayList<AddProductHelper> arrayList;
    Context context;
    OnNoteListenerList mOnNoteListener;

    public ProductListAdapter(ArrayList<AddProductHelper> arrayList, Context context, OnNoteListenerList mOnNoteListener) {
        this.arrayList = arrayList;
        this.context = context;
        this.mOnNoteListener = mOnNoteListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_card,parent,false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view, mOnNoteListener);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ProductViewHolder holder, int position) {
        AddProductHelper helper = arrayList.get(position);
        holder.productName.setText(helper.getName());
        holder.productPrice.setText(helper.getPrice());
        Picasso.with(context)
                .load(helper.getImageUrl())
                .placeholder(R.mipmap.ic_launcher_round)
                .fit()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView productName, productPrice;
        OnNoteListenerList onNoteListenerList;
        public ProductViewHolder(@NonNull View itemView, OnNoteListenerList onNoteListenerList) {
            super(itemView);
            imageView = itemView.findViewById(R.id.list_product_image);
            productName = itemView.findViewById(R.id.list_product_name);
            productPrice = itemView.findViewById(R.id.list_product_price);
            this.onNoteListenerList = onNoteListenerList;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListenerList.onNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteListenerList{
        void onNoteClick(int position);
    }
}
