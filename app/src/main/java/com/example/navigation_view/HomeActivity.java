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

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    RecyclerView recyclerView3;
    RecyclerView.Adapter adapter;

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

        featuredRecycler();
        featuredRecycler2();
        featuredRecycler3();

        Bundle extra = getIntent().getExtras();
        if(extra != null){
            shopId = extra.getString("shopId");
        }
        Log.i("shopId",shopId);


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
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_categories:
                startActivity(new Intent(getApplicationContext(), CategoriesActivity.class));
                break;
            case R.id.nav_add_product:
                startActivity(new Intent(getApplicationContext(), AddProductActivity.class));
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void featuredRecycler3() {
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.bart,"Bart","100$"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.homer,"Homer","200$"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.dollar,"Dollar","300$"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.red,"Red","400$"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.meter_saw,"Meter Saw","500$"));

        adapter = new FeaturedAdapter(featuredLocations);
        recyclerView3.setAdapter(adapter);
    }

    private void featuredRecycler2() {
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.bart,"Bart","100$"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.homer,"Homer","200$"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.dollar,"Dollar","300$"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.red,"Red","400$"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.meter_saw,"Meter Saw","500$"));

        adapter = new FeaturedAdapter(featuredLocations);
        recyclerView2.setAdapter(adapter);
    }

    private void featuredRecycler() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.bart,"Bart","100$"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.homer,"Homer","200$"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.dollar,"Dollar","300$"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.red,"Red","400$"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.meter_saw,"Meter Saw","500$"));

        adapter = new FeaturedAdapter(featuredLocations);
        recyclerView.setAdapter(adapter);

    }



}


