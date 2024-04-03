package com.example.growwise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.SharedPreferences;

import com.example.growwise.databinding.ActivityLocationsListBinding;
import com.example.growwise.databinding.ActivityUserProfileBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserProfile extends AppCompatActivity {

    EditText profileName, profileDOB, profilePhNo;
    Button logoutBtn;
    private ActivityUserProfileBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home)
                .build();


        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        // Get references to EditText views
        profileName = findViewById(R.id.profileName);
        profileDOB = findViewById(R.id.profileDOB);
        profilePhNo = findViewById(R.id.profilePhNo);
        logoutBtn = findViewById(R.id.logoutBtn);

        loadProfileData();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("userDetails", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(UserProfile.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadProfileData() {
        SharedPreferences preferences = getSharedPreferences("userDetails", MODE_PRIVATE);

        profileName.setText(preferences.getString("username", ""));
        profileDOB.setText(preferences.getString("DOB", ""));
        profilePhNo.setText(preferences.getString("phoneNumber", ""));

        profileName.setEnabled(false);
        profileDOB.setEnabled(false);
        profilePhNo.setEnabled(false);
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
