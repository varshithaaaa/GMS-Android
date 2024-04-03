package com.example.growwise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.growwise.databinding.ActivityAddNewLocationBinding;
import com.example.growwise.databinding.ActivityEmployeesListBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class AddNewLocation extends AppCompatActivity {

    EditText placeET, noOfWorkersET, contactET;
    Spinner managerIncSpinner, supervisorIncSpinner;
    Button addNewLocationBtn;
    private DBHelper db;
    private ActivityAddNewLocationBinding binding;
    private int managerPos, supervisorPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home)
                .build();

        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        db = new DBHelper(this);

        placeET = findViewById(R.id.placeET);
//        noOfWorkersET = findViewById(R.id.noOfWorkersET);
//        contactET = findViewById(R.id.contactET);
//        managerIncSpinner = findViewById(R.id.managerIncSpinner);
//        supervisorIncSpinner = findViewById(R.id.supervisorIncSpinner);
        addNewLocationBtn = findViewById(R.id.addNewLocation);

        User defaultUser = new User();
        defaultUser.setUserID(-1);

        List<User> supervisorList = db.getUsersByDesignation("Supervisor");

        defaultUser.setUserName("Select Supervisor");
        supervisorList.add(0, defaultUser);

        List<String> supervisorNames = new ArrayList<>();
        final List<Integer> supervisorIds = new ArrayList<>();
        for (User supervisor : supervisorList) {
            supervisorNames.add(supervisor.getUserName());
            supervisorIds.add(supervisor.getUserID());
        }

//        ArrayAdapter<String> supervisorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, supervisorNames);
//        supervisorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        supervisorIncSpinner.setAdapter(supervisorAdapter);

        User defaultUser2 = new User();
        defaultUser2.setUserID(-1);
        // Fetch users with designation "Manager" and populate the manager spinner
        List<User> managerList = db.getUsersByDesignation("Manager");
        List<String> managerNames = new ArrayList<>();
        final List<Integer> managerIds = new ArrayList<>();

        defaultUser2.setUserName("Select Manager");
        managerList.add(0, defaultUser2);

        for (User manager : managerList) {
            managerNames.add(manager.getUserName());
            managerIds.add(manager.getUserID());
        }

//        ArrayAdapter<String> managerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, managerNames);
//        managerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        managerIncSpinner.setAdapter(managerAdapter);

        // Set item selected listener for supervisor spinner
//        supervisorIncSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                supervisorPos = position;
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // Do nothing here
//            }
//        });
//
//        // Set item selected listener for manager spinner
//        managerIncSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                managerPos = position;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // Do nothing here
//            }
//        });
//


        addNewLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String placeName = placeET.getText().toString();

                boolean isAdded = db.addLocation(placeName, -1, -1, 0, null);
                if(isAdded) {
                    Toast.makeText(getApplicationContext(), "Location added", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), LocationsList.class);
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
            Intent intent  = new Intent(getApplicationContext(), AdminHome.class);
            startActivity(intent);
            finish();
        }
        return true;
    };
}
