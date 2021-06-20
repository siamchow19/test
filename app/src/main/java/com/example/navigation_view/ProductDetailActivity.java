package com.example.navigation_view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ProductDetailActivity extends AppCompatActivity {
    String shopId, productId;
    DatabaseReference databaseReference;
    ImageView imageView;
    TextView name, price, description, size;
    Button update;
    HashMap<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);


        imageView = findViewById(R.id.detail_image);
        name = findViewById(R.id.detail_name);
        price = findViewById(R.id.detail_price);
        description = findViewById(R.id.detail_product_desc);
        update = findViewById(R.id.update_button);
        size = findViewById(R.id.detail_size);
        map = new HashMap<>();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            productId = bundle.getString("productId");
            shopId = bundle.getString("shopId");
        }
        Log.i("productId", productId);//log check
        loadData();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UpdateActivity.class);
                intent.putExtra("productId",productId);
                startActivity(intent);
            }
        });


    }

    private void loadData() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Products").child(productId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String productName = snapshot.child("name").getValue().toString();
                String productPrice = snapshot.child("price").getValue().toString();
                String productDetail = snapshot.child("productDesc").getValue().toString();
                String imageUrl = snapshot.child("imageUrl").getValue().toString();

                name.setText(productName);
                price.setText(productPrice);
                description.setText(productDetail);

                Picasso.with(getApplicationContext())
                        .load(imageUrl)
                        .placeholder(R.mipmap.ic_launcher_round)
                        .fit()
                        .into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("sizes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String key = dataSnapshot.getKey();
                    String value = dataSnapshot.getValue().toString();
                    Log.i("result ", key+" "+value);
                    map.put(key, value);
                }
                Iterator itr = map.entrySet().iterator();
                size.setText("Sizes");
                while(itr.hasNext()){
                    Map.Entry pair = (Map.Entry) itr.next();
                    size.setText(size.getText() + "\n" + pair.getKey() + " : " + pair.getValue());
                    //iterator.remove();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}