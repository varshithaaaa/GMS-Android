package com.example.growwise;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.growwise.databinding.ActivitySupervisorHomeBinding;
import com.example.growwise.databinding.ActivityViewjobDetailsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ViewjobDetails extends AppCompatActivity {

    private TextView jobIdTextView, supervisorTextView, jobTypeTextView, managerTextView, dateTextView, startTimeTextView, endTimeTextView;
    private ActivityViewjobDetailsBinding binding;
    Button nextbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewjobDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home)
                .build();

        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        jobIdTextView = findViewById(R.id.jobIdTextView);
        jobTypeTextView = findViewById(R.id.jobTypeTextView);
        managerTextView = findViewById(R.id.managerTextView);
        supervisorTextView = findViewById(R.id.supervisorTextView);
        dateTextView = findViewById(R.id.dateTextView);
        startTimeTextView = findViewById(R.id.startTimeTextView);
        endTimeTextView = findViewById(R.id.endTimeTextView);
        nextbtn = findViewById(R.id.nextBtn);

        SharedPreferences preferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String userDesignation = preferences.getString("designation", "Admin");

        if(!userDesignation.equalsIgnoreCase("Manager")) {
            nextbtn.setVisibility(View.GONE);
        }



        Intent intent = getIntent();
        if (intent != null) {
            int jobId = intent.getIntExtra("jobId", -1);
            String jobType = intent.getStringExtra("jobType");
            String managerIncharge = intent.getStringExtra("managerIncharge");
            String supervisorIncharge = intent.getStringExtra("supervisorIncharge");
            String date = intent.getStringExtra("date");
            String startTime = intent.getStringExtra("startTime");
            String endTime = intent.getStringExtra("endTime");

            jobIdTextView.setText("1. Job ID: " + jobId);
            jobTypeTextView.setText("2. Job Type: " + jobType);
            managerTextView.setText("3. Manager Incharge: " + managerIncharge);
            supervisorTextView.setText("4. Supervisor Incharge: "+supervisorIncharge);
            dateTextView.setText("5. Date: " + date);
            startTimeTextView.setText("6. Start Time: " + startTime);
        }

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intent != null) {
                    Intent i = new Intent(ViewjobDetails.this, Rating.class);
                    i.putExtra("supervisor", intent.getStringExtra("supervisorIncharge"));
                    startActivity(i);
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