package com.example.navigation_view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateActivity extends AppCompatActivity {
    Spinner spinner;
    EditText quantity,price;
    Button addButton, confirmButton;
    DatabaseReference databaseReference;
    String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        spinner = findViewById(R.id.update_spinner_size);
        quantity = findViewById(R.id.update_product_quantity);
        price = findViewById(R.id.update_product_price);
        addButton = findViewById(R.id.update_quantity_button);
        confirmButton = findViewById(R.id.update_confirm);
        databaseReference = FirebaseDatabase.getInstance().getReference("Products");

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            productId = bundle.getString("productId");
        }

    }

    private void update() {
        String updatePrice = price.getText().toString();
        String updateQuantity = quantity.getText().toString();

        if(TextUtils.isEmpty(updatePrice) && TextUtils.isEmpty(updateQuantity)){
            Toast.makeText(this, "Update Something", Toast.LENGTH_SHORT).show();
        }else {
            if(!TextUtils.isEmpty(updatePrice)){
                databaseReference.child(productId).child("price").setValue(updatePrice);
            }

        }
    }
}