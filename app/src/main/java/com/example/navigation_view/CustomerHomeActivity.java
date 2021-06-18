package com.example.navigation_view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class CustomerHomeActivity extends AppCompatActivity {
    private String buyerId = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        Bundle extra = getIntent().getExtras();
        if(extra != null){
            buyerId = extra.getString("buyerId");
        }
        Log.i("customerId: ", buyerId);
    }
}