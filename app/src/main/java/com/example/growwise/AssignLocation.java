package com.example.growwise;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class AssignLocation extends AppCompatActivity {
    private DBHelper db;
    Button assignLoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_location);
        db = new DBHelper(getApplicationContext());
        List<User> workerList = db.getWorkers();
        assignLoc = findViewById(R.id.assignLocation);

        RecyclerView recyclerView = findViewById(R.id.selectableNamesRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        WorkerListAdapter adapter = new WorkerListAdapter(workerList);
        recyclerView.setAdapter(adapter);
        User defaultUser = new User();
        defaultUser.setUserID(-1);

        List<LocationsCardModel> locationsList = db.getAllLocations();
        List<User> supervisorsList = db.getUsersByDesignation("Supervisor");

        defaultUser.setUserName("Select Supervisor");
        supervisorsList.add(0, defaultUser);
        List<String> supervisorNames = new ArrayList<>();
        final List<Integer> supervisorIds = new ArrayList<>();
        for (User supervisor : supervisorsList) {
            supervisorNames.add(supervisor.getUserName());
            supervisorIds.add(supervisor.getUserID());
        }

        List<String> locationNames = new ArrayList<>();
        final List<Integer> locationIds = new ArrayList<>();
        LocationsCardModel l = new LocationsCardModel();
        l.setLocationID(-1);
        l.setLocationName("Select Location");
        locationsList.add(0, l);
        for (LocationsCardModel location : locationsList) {
            locationNames.add(location.getLocationName());
            locationIds.add(location.getLocationID());
        }

        // Set up the location spinner
        Spinner localitySpinner = findViewById(R.id.localitySpinner);
        ArrayAdapter<String> localityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locationNames);
        localityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        localitySpinner.setAdapter(localityAdapter);

        // Set up the supervisor spinner
        Spinner supervisorSpinner = findViewById(R.id.supervisorIncSpinner);
        ArrayAdapter<String> supervisorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, supervisorNames);
        supervisorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        supervisorSpinner.setAdapter(supervisorAdapter);

        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        SharedPreferences preferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        int currentUserId = preferences.getInt("userId", -1);

        assignLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> selectedWorkerIds = adapter.getSelectedWorkerIds();
                int selectedLocationId = locationIds.get(localitySpinner.getSelectedItemPosition());
                int selectedSupervisorId = supervisorIds.get(supervisorSpinner.getSelectedItemPosition());

                String workerIdsString = TextUtils.join(",", selectedWorkerIds);
                boolean updated = db.updateLocation(selectedLocationId, selectedSupervisorId, workerIdsString, currentUserId);
                if(updated) {
                    Toast.makeText(getApplicationContext(), "Location Assigned!", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), AdminHome.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Error! Please try again.", Toast.LENGTH_LONG).show();

                }
            }
        });

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