package com.example.navigation_view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.HashMap;

public class AddProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    Spinner spinnerCat;
    Spinner spinnerSubCat;
    Spinner spinnerSize;
    Button selectImage;
    Button addButton, showButton, addQuantity;
    ImageView addProductImage;
    private Uri imageUri;
    EditText productDetail, price, quantity, productName;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    HashMap<String, String> map;
    String addquantity;

    FirebaseAuth auth;

    String shopId;

    private static final int IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        toolbar = findViewById(R.id.toolbar_add);
        drawerLayout = findViewById(R.id.drawer_layout_add);
        navigationView = findViewById(R.id.nav_view_add);

        auth = FirebaseAuth.getInstance();

        spinnerCat = findViewById(R.id.spinner_cat);
        spinnerSubCat = findViewById(R.id.spinner_sub_cat);
        spinnerSize = findViewById(R.id.spinner_size);
        selectImage = findViewById(R.id.select_image);
        addProductImage = findViewById(R.id.add_product_image);
        price = findViewById(R.id.add_product_price);
        productDetail = findViewById(R.id.product_desc);
        quantity = findViewById(R.id.add_product_quantity);
        productName = findViewById(R.id.add_product_name);

        addButton = findViewById(R.id.add_button);
        addQuantity = findViewById(R.id.add_quantity);

        map = new HashMap<>();

        //shopId
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            shopId = bundle.getString("shopId");
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Products");
        storageReference = FirebaseStorage.getInstance().getReference("Products");

        //Spinner Category
        String[] items = new String[]{"Women's Fashion", "Men's Fashion", "Kid's Fashion"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinnerCat.setAdapter(adapter);
        spinnerCat.setOnItemSelectedListener(this);

        //toolbar and navigation drawer
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_add_product);


        //Image selection
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        //sending info to firebase
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        //add Quantity
        addQuantity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addquantity = quantity.getText().toString();
                map.put(spinnerSize.getSelectedItem().toString(), addquantity);
            }
        });

    }

    private void setMap() {
        if (spinnerCat.getSelectedItem().toString().equals("Men's Fashion") && spinnerSubCat.getSelectedItem().toString().equals("Punjabi")) {

            map.put("34", "0");
            map.put("36", "0");
            map.put("38", "0");
            map.put("40", "0");
            map.put("42", "0");
            map.put("44", "0");

        } else if (spinnerCat.getSelectedItem().toString().equals("Men's Fashion") && spinnerSubCat.getSelectedItem().toString().equals("T-shirt")) {
            map.put("S", "0");
            map.put("M", "0");
            map.put("L", "0");
            map.put("XL", "0");
            map.put("XXL", "0");

        } else if (spinnerCat.getSelectedItem().toString().equals("Men's Fashion") && spinnerSubCat.getSelectedItem().toString().equals("Pant")) {

            map.put("28", "0");
            map.put("30", "0");
            map.put("32", "0");
            map.put("34", "0");
            map.put("36", "0");
            map.put("38", "0");
            map.put("40", "0");

        }else if (spinnerCat.getSelectedItem().toString().equals("Women's Fashion") && spinnerSubCat.getSelectedItem().toString().equals("Saree")) {

            map.put("6 yard", "0");

        }else if (spinnerCat.getSelectedItem().toString().equals("Women's Fashion") && spinnerSubCat.getSelectedItem().toString().equals("Pant")) {

            map.put("26","0");
            map.put("28", "0");
            map.put("30", "0");
            map.put("32", "0");
            map.put("34", "0");
            map.put("36", "0");
            map.put("38", "0");
            map.put("40", "0");

        }else if (spinnerCat.getSelectedItem().toString().equals("Kid's Fashion")) {

            map.put("0-6 month", "0");
            map.put("1 yr", "0");
            map.put("2 yr", "0");
            map.put("3 yr", "0");
            map.put("4 yr", "0");
            map.put("5 yr", "0");

        }else if (spinnerCat.getSelectedItem().toString().equals("Women's Fashion") && (spinnerSubCat.getSelectedItem().toString().equals("Shalwar Kameez") || spinnerSubCat.getSelectedItem().toString().equals("Kurta"))) {

            map.put("32", "0");
            map.put("34", "0");
            map.put("36", "0");
            map.put("38", "0");
            map.put("40", "0");
            map.put("42", "0");
            map.put("44", "0");

        }else if (spinnerCat.getSelectedItem().toString().equals("Women's Fashion") && spinnerSubCat.getSelectedItem().toString().equals("T-shirt")) {
            map.put("S", "0");
            map.put("M", "0");
            map.put("L", "0");
            map.put("XL", "0");
            map.put("XXL", "0");

        }
    }

    //sending ifo to firebase
    private void saveData() {
        String productDescription = productDetail.getText().toString();
        String productQuantity = quantity.getText().toString();
        String productPrice = price.getText().toString();
        String name = productName.getText().toString();

        if (productDescription.isEmpty() || productPrice.isEmpty() || productQuantity.isEmpty() || addProductImage.equals(R.mipmap.ic_launcher_round)) {
            Toast.makeText(this, "Empty credential", Toast.LENGTH_SHORT).show();
        } else {
            StorageReference ref = storageReference.child(System.currentTimeMillis() + "." + getExtension(imageUri));
            ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> myUri = taskSnapshot.getStorage().getDownloadUrl();
                    while (!myUri.isSuccessful()) ;
                    Uri imageUrl = myUri.getResult();


                    String uploadId = databaseReference.push().getKey();
                    AddProductHelper helper = new AddProductHelper(spinnerCat.getSelectedItem().toString(), spinnerSubCat.getSelectedItem().toString(), name, productDescription, productPrice, imageUrl.toString(), uploadId, shopId);
                    databaseReference.child(uploadId).setValue(helper);
                    databaseReference.child(uploadId).child("sizes").setValue(map);
                    Toast.makeText(AddProductActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                    spinnerCat.setEnabled(true);
                    spinnerSubCat.setEnabled(true);
                    addProductImage.setImageResource(R.mipmap.ic_launcher_round);
                    productDetail.setText(null);
                    price.setText(null);
                    quantity.setText(null);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddProductActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private String getExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    //Image Selection
    private void openFileChooser() {
        spinnerCat.setEnabled(false);
        spinnerSubCat.setEnabled(false);
        setMap();
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.with(this).load(imageUri).into(addProductImage);
        }
    }

    //Sub Category according to category
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (spinnerCat.getSelectedItem().equals("Women's Fashion")) {
            ArrayAdapter adapterSubCat = ArrayAdapter.createFromResource(this, R.array.women, android.R.layout.simple_spinner_dropdown_item);
            spinnerSubCat.setAdapter(adapterSubCat);
        } else if (spinnerCat.getSelectedItem().equals("Men's Fashion")) {
            ArrayAdapter adapterSubCat = ArrayAdapter.createFromResource(this, R.array.men, android.R.layout.simple_spinner_dropdown_item);
            spinnerSubCat.setAdapter(adapterSubCat);
        } else if(spinnerCat.getSelectedItem().equals("Kid's Fashion")){
            ArrayAdapter adapterSubCat = ArrayAdapter.createFromResource(this, R.array.kid, android.R.layout.simple_spinner_dropdown_item);
            spinnerSubCat.setAdapter(adapterSubCat);
        }
        //Size Spinner according to sub category
        spinnerSubCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerCat.getSelectedItem().toString().equals("Men's Fashion") && spinnerSubCat.getSelectedItem().toString().equals("T-shirt")) {
                    ArrayAdapter adapterSize = ArrayAdapter.createFromResource(getApplicationContext(), R.array.T_shirt, R.layout.support_simple_spinner_dropdown_item);
                    spinnerSize.setAdapter(adapterSize);
                } else if (spinnerCat.getSelectedItem().toString().equals("Men's Fashion") && spinnerSubCat.getSelectedItem().toString().equals("Punjabi")) {
                    ArrayAdapter adapterSize = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Shirt_punjabi, R.layout.support_simple_spinner_dropdown_item);
                    spinnerSize.setAdapter(adapterSize);
                } else if (spinnerCat.getSelectedItem().toString().equals("Men's Fashion") && spinnerSubCat.getSelectedItem().toString().equals("Pant")) {
                    ArrayAdapter adapterSize = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Men_pant, R.layout.support_simple_spinner_dropdown_item);
                    spinnerSize.setAdapter(adapterSize);
                } else if (spinnerCat.getSelectedItem().toString().equals("Women's Fashion") && spinnerSubCat.getSelectedItem().toString().equals("Shalwar Kameez")) {
                    ArrayAdapter adapterSize = ArrayAdapter.createFromResource(getApplicationContext(), R.array.WomenFashion, R.layout.support_simple_spinner_dropdown_item);
                    spinnerSize.setAdapter(adapterSize);
                }else if (spinnerCat.getSelectedItem().toString().equals("Women's Fashion") && spinnerSubCat.getSelectedItem().toString().equals("Kurta")) {
                    ArrayAdapter adapterSize = ArrayAdapter.createFromResource(getApplicationContext(), R.array.WomenFashion, R.layout.support_simple_spinner_dropdown_item);
                    spinnerSize.setAdapter(adapterSize);
                }else if (spinnerCat.getSelectedItem().toString().equals("Kid's Fashion")) {
                    ArrayAdapter adapterSize = ArrayAdapter.createFromResource(getApplicationContext(), R.array.kids_fashion, R.layout.support_simple_spinner_dropdown_item);
                    spinnerSize.setAdapter(adapterSize);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intentHome = new Intent(AddProductActivity.this, HomeActivity.class);
                intentHome.putExtra("shopId", shopId);
                startActivity(intentHome);
                finish();
                break;
            case R.id.nav_categories:
                Intent intentCat = new Intent(AddProductActivity.this, CategoriesActivity.class);
                intentCat.putExtra("shopId", shopId);
                startActivity(intentCat);
                finish();
                break;
            case R.id.nav_add_product:
                break;
            case R.id.nav_logout:
                auth.signOut();
                startActivity(new Intent(AddProductActivity.this, LogInActivity.class));
                finish();

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}

