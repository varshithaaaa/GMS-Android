package com.example.growwise;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.growwise.databinding.ActivityLocationsListBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class LocationsList extends AppCompatActivity {
    private DBHelper db;
    private Button addLoc;
    private ActivityLocationsListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHelper(this);

        binding = ActivityLocationsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home)
                .build();


        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        SharedPreferences preferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String userDesignation = preferences.getString("designation", "Admin");

        addLoc = findViewById(R.id.addLocationBtn);

        if(!userDesignation.equalsIgnoreCase("admin")) {
            addLoc.setVisibility(View.INVISIBLE);
        }

        addLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddNewLocation.class);
                startActivity(intent);
            }
        });

        ArrayList<LocationsCardModel> CardModelArrayList = new ArrayList<>();
        CardModelArrayList = (ArrayList<LocationsCardModel>) db.getAllLocations();
        RecyclerView cardsRV = findViewById(R.id.idRVLocationCards);
        LocationsCardAdapter courseAdapter = new LocationsCardAdapter(this, CardModelArrayList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        cardsRV.setLayoutManager(gridLayoutManager);
        cardsRV.setAdapter(courseAdapter);

        courseAdapter.setOnItemClickListener(new LocationsCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LocationsCardModel l) {
                Intent intent = new Intent(getApplicationContext(), ViewLocationDetails.class);
                intent.putExtra("locationName", l.getLocationName());
                intent.putExtra("locationId", l.getLocationID());
                intent.putExtra("managerIncharge", l.getManagerIncharge());
                intent.putExtra("supervisorIncharge", l.getSupervisorIncharge());
                intent.putExtra("noOfWorkers", l.getNoOfWorkers());
                intent.putExtra("phNo", l.getPhoneNumber());
                startActivity(intent);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        int itemId = item.getItemId();
        if (itemId == R.id.navigation_home) {
            Intent intent  = new Intent(getApplicationContext(), AdminHome.class);
            startActivity(intent);
            finish();
        }
        return true;
    };
}