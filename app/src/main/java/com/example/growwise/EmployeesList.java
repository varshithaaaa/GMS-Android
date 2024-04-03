package com.example.growwise;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.growwise.databinding.ActivityEmployeesListBinding;

import java.util.ArrayList;

public class EmployeesList extends AppCompatActivity {

    private ActivityEmployeesListBinding binding;
    private Button addEmployeeBtn;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeesListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home)
                .build();

        db = new DBHelper(this);

        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        RecyclerView cardsRV = findViewById(R.id.idRVResidentCards);


        addEmployeeBtn = findViewById(R.id.addEmployeeBtn);
        SharedPreferences preferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String userDesignation = preferences.getString("designation", "Manager");
        int userId = preferences.getInt("userId", -1);
        if(userDesignation.equalsIgnoreCase("Admin")) {

            addEmployeeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), NewEmployee.class);
                    startActivity(intent);
                }
            });
            ArrayList<User> CardModelArrayList = new ArrayList<>();
            CardModelArrayList = (ArrayList<User>) db.getAllUsers();
            EmployeesCardAdapter courseAdapter = new EmployeesCardAdapter(this, CardModelArrayList, userDesignation, userId);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            cardsRV.setLayoutManager(linearLayoutManager);
            cardsRV.setAdapter(courseAdapter);
        } else if(userDesignation.equalsIgnoreCase("Manager")) {
            addEmployeeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), AssignLocation.class);
                    startActivity(intent);
                }
            });

            ArrayList<User> CardModelArrayList = new ArrayList<>();
            CardModelArrayList = (ArrayList<User>) db.getUsersByDesignation("Supervisor");
            EmployeesCardAdapter courseAdapter = new EmployeesCardAdapter(this, CardModelArrayList, userDesignation, userId);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            cardsRV.setLayoutManager(linearLayoutManager);
            cardsRV.setAdapter(courseAdapter);
        } else {
            addEmployeeBtn.setVisibility(View.GONE);
            ArrayList<User> CardModelArrayList = new ArrayList<>();
            CardModelArrayList = (ArrayList<User>) db.getUsersByDesignation("Worker");
            EmployeesCardAdapter courseAdapter = new EmployeesCardAdapter(this, CardModelArrayList, userDesignation, userId);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            cardsRV.setLayoutManager(linearLayoutManager);
            cardsRV.setAdapter(courseAdapter);
        }

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