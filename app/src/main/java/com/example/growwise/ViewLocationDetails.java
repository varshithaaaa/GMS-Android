package com.example.growwise;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.growwise.databinding.ActivityViewLocationDetailsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class ViewLocationDetails extends AppCompatActivity {

    TextView location, manager, supervisor, noOfWorkersTV, phNoTV;
    private ActivityViewLocationDetailsBinding binding;

    Button contact;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewLocationDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home)
                .build();

        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        SharedPreferences preferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String userDesignation = preferences.getString("designation", "Admin");

        contact = findViewById(R.id.contactLocation);

        if(!userDesignation.equalsIgnoreCase("manager")) {
            contact.setVisibility(View.INVISIBLE);
        }

        location = findViewById(R.id.locationName);
        manager = findViewById(R.id.managerInCharge);
        supervisor = findViewById(R.id.supervisorInCharge);
        noOfWorkersTV = findViewById(R.id.noOfWorkers);
//        phNoTV = findViewById(R.id.locationPhNo);

        Intent intent = getIntent();
        if (intent != null) {
            String locationName = intent.getStringExtra("locationName");
            int id = intent.getIntExtra("locationId", 0);
            String managerIncharge = intent.getStringExtra("managerIncharge");
            String supervisorIncharge = intent.getStringExtra("supervisorIncharge");
            int noOfWorkers = intent.getIntExtra("noOfWorkers", 0);
            String phNo = intent.getStringExtra("phNo");

            location.setText(locationName);
            manager.setText("Manager Incharge: "+managerIncharge);
            supervisor.setText("Supervisor Incharge: "+supervisorIncharge);
            noOfWorkersTV.setText("No of Workers: "+noOfWorkers);
//            phNoTV.setText("Phone Number: "+phNo);
        }

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(ViewLocationDetails.this, Contact.class);
                    i.putExtras(Objects.requireNonNull(intent.getExtras()));

                    startActivity(i);
//                    Toast.makeText(getApplicationContext(),"Notification Sent",Toast.LENGTH_LONG).show();
                }catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),"Error! Please try again.",Toast.LENGTH_LONG).show();
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