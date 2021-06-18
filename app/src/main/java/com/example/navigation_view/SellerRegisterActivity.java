package com.example.navigation_view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SellerRegisterActivity extends AppCompatActivity {

    private EditText name, mobile, email, password, shopName, address;
    private Button register;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_register);

        name = findViewById(R.id.seller_name);
        mobile = findViewById(R.id.seller_mobile);
        email = findViewById(R.id.seller_email);
        password = findViewById(R.id.seller_password);
        shopName = findViewById(R.id.seller_shop_name);
        address = findViewById(R.id.seller_address);
        register = findViewById(R.id.register_button);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Shops");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    private void loadData() {
        String sellerName = name.getText().toString();
        String sellerMobile = mobile.getText().toString();
        String sellerEmail = email.getText().toString();
        String sellerPassword = password.getText().toString();
        String sellerShopName = shopName.getText().toString();
        String sellerAddress = address.getText().toString();
        if (TextUtils.isEmpty(sellerName) || TextUtils.isEmpty(sellerMobile) || TextUtils.isEmpty(sellerEmail) || TextUtils.isEmpty(sellerPassword) || TextUtils.isEmpty(sellerShopName) || TextUtils.isEmpty(sellerAddress)) {
            Toast.makeText(this, "Empty Credential", Toast.LENGTH_SHORT).show();
        } else if (sellerPassword.length() < 6) {
            Toast.makeText(this, "Password too short", Toast.LENGTH_SHORT).show();
        } else {
            userRegister(sellerEmail, sellerPassword, sellerName, sellerMobile, sellerShopName, sellerAddress);
        }

    }

    private void userRegister(String email, String password, String name, String mobile, String shopName, String address) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SellerRegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    String uploadId = databaseReference.push().getKey();
                    ShopInfo shopInfo = new ShopInfo(name, mobile, email, shopName, address, uploadId);
                    databaseReference.child(uploadId).setValue(shopInfo);

                    Intent intent = new Intent(SellerRegisterActivity.this, HomeActivity.class);
                    intent.putExtra("shopId", uploadId);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(SellerRegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}