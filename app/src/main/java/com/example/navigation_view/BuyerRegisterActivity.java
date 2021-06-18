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

public class BuyerRegisterActivity extends AppCompatActivity {

    private EditText name, email, password;
    private Button registerButton;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_register);

        name = findViewById(R.id.buyer_name);
        email = findViewById(R.id.buyer_email);
        password = findViewById(R.id.buyer_password);
        registerButton = findViewById(R.id.register_button_buyer);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Customer");

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    private void loadData() {
        String buyerName, buyerEmail, buyerPassword;
        buyerName = name.getText().toString();
        buyerEmail = email.getText().toString();
        buyerPassword = password.getText().toString();

        if(TextUtils.isEmpty(buyerEmail) || TextUtils.isEmpty(buyerName) || TextUtils.isEmpty(buyerPassword)){
            Toast.makeText(this, "Empty Credential", Toast.LENGTH_SHORT).show();
        }else if(buyerPassword.length() < 6){
            Toast.makeText(this, "Password too small", Toast.LENGTH_SHORT).show();
        }else{
            buyerRegister(buyerEmail, buyerPassword, buyerName);
        }
    }

    private void buyerRegister(String email, String password, String name) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(BuyerRegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    String uploadId = databaseReference.push().getKey();
                    BuyerInfo buyerInfo = new BuyerInfo(name, email, uploadId);
                    databaseReference.child(uploadId).setValue(buyerInfo);

                    Intent intent = new Intent(getApplicationContext(), CustomerHomeActivity.class);
                    intent.putExtra("buyerId", uploadId);
                    startActivity(intent);

                    finish();

                }else{
                    Toast.makeText(BuyerRegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}