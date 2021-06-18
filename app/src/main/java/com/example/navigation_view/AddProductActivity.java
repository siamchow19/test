package com.example.navigation_view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.URI;

public class AddProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerCat;
    Spinner spinnerSubCat;
    Button selectImage;
    ImageView addProductImage;
    private Uri imageUri;

    private static final int IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        spinnerCat = findViewById(R.id.spinner_cat);
        spinnerSubCat = findViewById(R.id.spinner_sub_cat);
        selectImage = findViewById(R.id.select_image);
        addProductImage = findViewById(R.id.add_product_image);

        //Spinner Category
        String[] items = new String[]{"Women's Fashion", "Men's Fashion", "Kid's Fashion"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinnerCat.setAdapter(adapter);
        spinnerCat.setOnItemSelectedListener(this);


        //Image selection
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

    }
    //Image Selection
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData()!=null){
            imageUri = data.getData();
            Picasso.with(this).load(imageUri).into(addProductImage);
        }
    }

    //Sub Category
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (spinnerCat.getSelectedItem().equals("Women's Fashion")) {
            ArrayAdapter adapterSubCat = ArrayAdapter.createFromResource(this, R.array.women, android.R.layout.simple_spinner_dropdown_item);
            spinnerSubCat.setAdapter(adapterSubCat);
        } else if (spinnerCat.getSelectedItem().equals("Men's Fashion")) {
            ArrayAdapter adapterSubCat = ArrayAdapter.createFromResource(this, R.array.men, android.R.layout.simple_spinner_dropdown_item);
            spinnerSubCat.setAdapter(adapterSubCat);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    //ADD BUTTON Clicked
    public void onClickAddButton(View view) {
        Log.i("Category:", spinnerCat.getSelectedItem().toString());
        Log.i("Sub Category:", spinnerSubCat.getSelectedItem().toString());
        Log.i("Description:", "Pressed");
        Log.i("Size:", "Pressed" );
        Log.i("Quantity:", "Pressed" );
        Log.i("Price:", "Pressed" );
    }
}

