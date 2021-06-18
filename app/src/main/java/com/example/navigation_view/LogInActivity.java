package com.example.navigation_view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends AppCompatActivity {

    private EditText email, password;
    private Button login;
    private TextView signupSeller, signupBuyer;
    private FirebaseAuth auth;
    private boolean isCustomer = false;


    private DatabaseReference databaseReference1, databaseReference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        login = findViewById(R.id.login_button);
        signupBuyer = findViewById(R.id.signup_buyer);
        signupSeller = findViewById(R.id.signup_seller);
        auth = FirebaseAuth.getInstance();
        databaseReference1 = FirebaseDatabase.getInstance().getReference("Customer");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Shops");

        signupSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, SellerRegisterActivity.class));
                finish();
            }
        });

        signupBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BuyerRegisterActivity.class));
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                userLogIN(userEmail, userPassword);
                finish();
            }
        });
    }

    private void userLogIN(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LogInActivity.this, "Log in successful", Toast.LENGTH_SHORT).show();

                databaseReference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            BuyerInfo buyerInfo = dataSnapshot.getValue(BuyerInfo.class);
                            if (email.trim().equals(buyerInfo.getEmail())) {
                                isCustomer = true;
                                Intent intent = new Intent(LogInActivity.this, CustomerHomeActivity.class);
                                intent.putExtra("buyerId", buyerInfo.getBuyerId());
                                startActivity(intent);
                                return;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                if (!isCustomer) {
                    databaseReference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                ShopInfo shopInfo = dataSnapshot.getValue(ShopInfo.class);
                                if (email.trim().equals(shopInfo.getEmail())) {
                                    Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                                    intent.putExtra("shopId", shopInfo.getShopId());
                                    startActivity(intent);

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });
    }


}