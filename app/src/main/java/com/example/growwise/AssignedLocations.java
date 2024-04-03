package com.example.growwise;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class AssignedLocations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_locations);

        RecyclerView recyclerView = findViewById(R.id.assignedLocationsRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DBHelper dbHelper = new DBHelper(this);
        List<AssignedLocationsModel> assignedLocationsList = dbHelper.getAssignedLocations();

        AssignedLocationAdapter adapter = new AssignedLocationAdapter(this, assignedLocationsList);
        recyclerView.setAdapter(adapter);

        Button assignLocBtn = findViewById(R.id.assignLocBtn);

        assignLocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(), AssignLocation.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        int itemId = item.getItemId();
        if (itemId == R.id.navigation_home) {
            SharedPreferences preferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
            String userDesignation = preferences.getString("designation", "Manager");

            if(!userDesignation.equalsIgnoreCase("Supervisor")) {
                Intent intent = new Intent(getApplicationContext(), AdminHome.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(getApplicationContext(), SupervisorHome.class);
                startActivity(intent);
                finish();
            }
        }
        return true;
    };
}
