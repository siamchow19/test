package com.example.navigation_view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    String category, subcategory, shopId;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ProductListAdapter adapter;
    ArrayList<AddProductHelper> list;
    ProductListAdapter.OnNoteListenerList onNoteListenerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            category = bundle.getString("Category");
            subcategory = bundle.getString("Sub Category");
            shopId = bundle.getString("shopId");
        }
        Log.i("bundle", category + subcategory + shopId);

        databaseReference = FirebaseDatabase.getInstance().getReference("Products");
        recyclerView = findViewById(R.id.recycler_product_list);
        list = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    AddProductHelper helper = dataSnapshot.getValue(AddProductHelper.class);
                    if(helper.getShopId().equals(shopId) && helper.getCategory().equals(category) && helper.getSubCategory().equals(subcategory)){
                        list.add(helper);
                    }
                }
                setOnClickListener();
                adapter = new ProductListAdapter(list, getApplicationContext(), onNoteListenerList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProductListActivity.this, "Error occurred on product list", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setOnClickListener(){
        onNoteListenerList = new ProductListAdapter.OnNoteListenerList() {
            @Override
            public void onNoteClick(int position) {
                Log.i("productID", list.get(position).getProductId());
                Intent intent = new Intent (ProductListActivity.this, ProductDetailActivity.class);
                intent.putExtra("productId",list.get(position).getProductId());
                intent.putExtra("shopId",shopId);
                startActivity(intent);
            }
        };
    }
}