package com.example.navigation_view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    RecyclerView recyclerView3;
    HomePageAdapter adapter, adapter2, adapter3;

    ArrayList<AddProductHelper> featuredLocations;
    ArrayList<AddProductHelper> featuredLocations2;
    ArrayList<AddProductHelper> featuredLocations3;

    DatabaseReference databaseReference;
    FirebaseAuth auth;

    HomePageAdapter.OnNoteListenerHome onNoteListenerHome, onNoteListenerHome2, onNoteListenerHome3;

    String shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar_home);
        drawerLayout = findViewById(R.id.drawer_layout_home);
        navigationView = findViewById(R.id.nav_view_home);

        recyclerView = findViewById(R.id.featured_recycler_home);
        recyclerView2 = findViewById(R.id.featured_recycler2_home);
        recyclerView3 = findViewById(R.id.featured_recycler3_home);

        databaseReference = FirebaseDatabase.getInstance().getReference("Products");
        auth = FirebaseAuth.getInstance();

        featuredRecycler();
        featuredRecycler2();
        featuredRecycler3();

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            shopId = extra.getString("shopId");
        }
        Log.i("shopId", shopId);


        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_categories:
                Intent intentCat = new Intent(HomeActivity.this, CategoriesActivity.class);
                intentCat.putExtra("shopId", shopId);
                startActivity(intentCat);
                finish();
                break;
            case R.id.nav_add_product:
                Intent intentAdd = new Intent(HomeActivity.this, AddProductActivity.class);
                intentAdd.putExtra("shopId", shopId);
                startActivity(intentAdd);
                finish();
                break;
            case R.id.nav_logout:
                auth.signOut();
                startActivity(new Intent(HomeActivity.this, LogInActivity.class));
                finish();

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void featuredRecycler() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        featuredLocations = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AddProductHelper helper = dataSnapshot.getValue(AddProductHelper.class);
                    if (helper.getCategory().equals("Women's Fashion") && helper.shopId.equals(shopId)) {
                        featuredLocations.add(helper);
                    }
                }
                setOnClickListener();
                adapter = new HomePageAdapter(featuredLocations, HomeActivity.this, onNoteListenerHome);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void featuredRecycler2() {
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        featuredLocations2 = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AddProductHelper helper = dataSnapshot.getValue(AddProductHelper.class);
                    if (helper.getCategory().equals("Men's Fashion") && helper.shopId.equals(shopId)) {
                        featuredLocations2.add(helper);
                    }
                }
                setOnClickListener();
                adapter2 = new HomePageAdapter(featuredLocations2, HomeActivity.this, onNoteListenerHome2);
                recyclerView2.setAdapter(adapter2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void featuredRecycler3() {
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        featuredLocations3 = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AddProductHelper helper = dataSnapshot.getValue(AddProductHelper.class);
                    if (helper.getCategory().equals("Kid's Fashion") && helper.shopId.equals(shopId)) {
                        featuredLocations3.add(helper);
                    }
                }
                setOnClickListener();
                adapter3 = new HomePageAdapter(featuredLocations3, HomeActivity.this, onNoteListenerHome3);
                recyclerView3.setAdapter(adapter3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setOnClickListener() {
        onNoteListenerHome = new HomePageAdapter.OnNoteListenerHome() {
            @Override
            public void onNoteClick(int position) {
                Log.i("ProductID", featuredLocations.get(position).getProductId());
                Intent intent = new Intent (HomeActivity.this, ProductDetailActivity.class);
                intent.putExtra("productId",featuredLocations.get(position).getProductId());
                intent.putExtra("shopId",shopId);
                startActivity(intent);
            }
        };

        onNoteListenerHome2 = new HomePageAdapter.OnNoteListenerHome() {
            @Override
            public void onNoteClick(int position) {
                Log.i("ProductID", featuredLocations2.get(position).getProductId());
                Intent intent = new Intent (HomeActivity.this, ProductDetailActivity.class);
                intent.putExtra("productId",featuredLocations2.get(position).getProductId());
                intent.putExtra("shopId",shopId);
                startActivity(intent);
            }
        };

        onNoteListenerHome3 = new HomePageAdapter.OnNoteListenerHome() {
            @Override
            public void onNoteClick(int position) {
                Log.i("ProductID", featuredLocations3.get(position).getProductId());
                Intent intent = new Intent (HomeActivity.this, ProductDetailActivity.class);
                intent.putExtra("productId",featuredLocations3.get(position).getProductId());
                intent.putExtra("shopId",shopId);
                startActivity(intent);
            }
        };
    }

}


