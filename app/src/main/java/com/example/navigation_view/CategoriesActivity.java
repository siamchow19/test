package com.example.navigation_view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    RecyclerView recyclerView3;
    RecyclerView.Adapter adapter;

    ArrayList<FeaturedHelperClass> featuredLocations;
    ArrayList<FeaturedHelperClass> featuredLocations2;
    ArrayList<FeaturedHelperClass> featuredLocations3;

    FeaturedAdapter.OnNoteListener onNoteListener, onNoteListener2, onNoteListener3;

    FirebaseAuth auth;

    String shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        toolbar = findViewById(R.id.toolbar_category);
        drawerLayout = findViewById(R.id.drawer_layout_cat);
        navigationView = findViewById(R.id.nav_view_category);

        recyclerView = findViewById(R.id.featured_recycler_category);
        recyclerView2 = findViewById(R.id.featured_recycler2_category);
        recyclerView3 = findViewById(R.id.featured_recycler3_category);

        auth = FirebaseAuth.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            shopId = bundle.getString("shopId");
        }

        featuredRecycler();
        featuredRecycler2();
        featuredRecycler3();


        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setCheckedItem(R.id.nav_categories);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void featuredRecycler() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.bart, "Shalwar Kameez"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.homer, "Kurta"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.dollar, "Saree"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.red, "T-shirt"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.meter_saw, "Sub-Category5"));
        setOnClickListener();
        adapter = new FeaturedAdapter(featuredLocations, this, onNoteListener);
        recyclerView.setAdapter(adapter);

    }

    private void featuredRecycler2() {
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        featuredLocations2 = new ArrayList<>();

        featuredLocations2.add(new FeaturedHelperClass(R.drawable.bart, "T-shirt"));
        featuredLocations2.add(new FeaturedHelperClass(R.drawable.homer, "Punjabi"));
        featuredLocations2.add(new FeaturedHelperClass(R.drawable.dollar, "Pant"));
        featuredLocations2.add(new FeaturedHelperClass(R.drawable.red, "Sub-Category4"));
        featuredLocations2.add(new FeaturedHelperClass(R.drawable.meter_saw, "Sub-Category5"));
        setOnClickListener();
        adapter = new FeaturedAdapter(featuredLocations2, this, onNoteListener2);
        recyclerView2.setAdapter(adapter);

    }

    private void featuredRecycler3() {
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        featuredLocations3 = new ArrayList<>();

        featuredLocations3.add(new FeaturedHelperClass(R.drawable.bart, "T-shirt"));
        featuredLocations3.add(new FeaturedHelperClass(R.drawable.homer, "Frock"));
        featuredLocations3.add(new FeaturedHelperClass(R.drawable.dollar, "Punjabi"));
        featuredLocations3.add(new FeaturedHelperClass(R.drawable.red, "Pant"));
        featuredLocations3.add(new FeaturedHelperClass(R.drawable.meter_saw, "Sub-Category5"));
        setOnClickListener();
        adapter = new FeaturedAdapter(featuredLocations3, this, onNoteListener3);
        recyclerView3.setAdapter(adapter);
    }

    private void setOnClickListener() {
        onNoteListener = new FeaturedAdapter.OnNoteListener() {
            @Override
            public void onNoteClick(int position) {
                Log.i("Position: ", featuredLocations.get(position).getName());
                Intent intent = new Intent(getApplicationContext(), ProductListActivity.class);
                intent.putExtra("Category", "Women's Fashion");
                intent.putExtra("Sub Category", featuredLocations.get(position).getName());
                intent.putExtra("shopId",shopId);
                startActivity(intent);

            }
        };

        onNoteListener2 = new FeaturedAdapter.OnNoteListener() {
            @Override
            public void onNoteClick(int position) {
                Log.i("Position: ", featuredLocations2.get(position).getName());
                Intent intent = new Intent(getApplicationContext(), ProductListActivity.class);
                intent.putExtra("Category", "Men's Fashion");
                intent.putExtra("Sub Category", featuredLocations2.get(position).getName());
                intent.putExtra("shopId",shopId);
                startActivity(intent);

            }
        };

        onNoteListener3 = new FeaturedAdapter.OnNoteListener() {
            @Override
            public void onNoteClick(int position) {
                Log.i("Position: ", featuredLocations3.get(position).getName());
                Intent intent = new Intent(getApplicationContext(), ProductListActivity.class);
                intent.putExtra("Category", "Kid's Fashion");
                intent.putExtra("Sub Category", featuredLocations3.get(position).getName());
                intent.putExtra("shopId",shopId);
                startActivity(intent);
            }
        };

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
                Intent intentHome = new Intent(CategoriesActivity.this, HomeActivity.class);
                intentHome.putExtra("shopId", shopId);
                startActivity(intentHome);
                finish();
                break;
            case R.id.nav_categories:
                break;
            case R.id.nav_add_product:
                Intent intentAdd = new Intent(CategoriesActivity.this, AddProductActivity.class);
                intentAdd.putExtra("shopId", shopId);
                startActivity(intentAdd);
                break;
            case R.id.nav_logout:
                auth.signOut();
                startActivity(new Intent(CategoriesActivity.this, LogInActivity.class));
                finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}