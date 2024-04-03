package com.example.growwise;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class AdminHome extends AppCompatActivity {

    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        RecyclerView cardsRV = findViewById(R.id.idRVCards);

        ArrayList<CardModel> CardModelArrayList = new ArrayList<CardModel>();

        db = new DBHelper(this);
        SharedPreferences preferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String userDesignation = preferences.getString("designation", "Manager");

        if(userDesignation.equalsIgnoreCase("admin")) {
            CardModelArrayList.add(new CardModel("Employee"));
            CardModelArrayList.add(new CardModel("Places"));
            CardModelArrayList.add(new CardModel("Reports"));
        } else if(userDesignation.equalsIgnoreCase("manager")) {
            CardModelArrayList.add(new CardModel("Assign Location"));
            CardModelArrayList.add(new CardModel("Places"));
            CardModelArrayList.add(new CardModel("Equipment and Materials Management"));
            CardModelArrayList.add(new CardModel("LeaderBoard"));
        } else if(userDesignation.equalsIgnoreCase("Supervisor")) {
            Intent intent  = new Intent(getApplicationContext(), SupervisorHome.class);
            startActivity(intent);
            finish();
        }
        CardAdapter courseAdapter = new CardAdapter(this, CardModelArrayList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        courseAdapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String name) {
                if(name.equals("Employee")) {
                    Intent intent  = new Intent(getApplicationContext(), EmployeesList.class);
                    startActivity(intent);
                } else if(name.equals("Places")) {
                    Intent intent  = new Intent(getApplicationContext(), LocationsList.class);
                    startActivity(intent);
                } else if(name.equals("Assign Location")) {
                    Intent intent  = new Intent(getApplicationContext(), AssignedLocations.class);
                    startActivity(intent);
                } else if(name.equals("Equipment and Materials Management")) {
                    Intent intent  = new Intent(getApplicationContext(), Assets.class);
                    intent.putExtra("from", "home");
                    startActivity(intent);
                } else if(name.equalsIgnoreCase("Reports")) {
                    Intent intent  = new Intent(getApplicationContext(), ViewJobs.class);
                    startActivity(intent);
                } else if(name.equalsIgnoreCase("LeaderBoard")) {
                    Intent intent  = new Intent(getApplicationContext(), LeaderBoard.class);
                    startActivity(intent);
                }
            }
        });
        cardsRV.setLayoutManager(linearLayoutManager);
        cardsRV.setAdapter(courseAdapter);
        cardsRV.setClickable(true);

        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if(!userDesignation.equalsIgnoreCase("Manager")) {
            bottomNav.setVisibility(View.GONE);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        SharedPreferences preferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String userDesignation = preferences.getString("designation", "Manager");

        if (!userDesignation.equalsIgnoreCase("Admin")) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(R.layout.custom_actionbar_layout);

            // Handle notification icon click
            View notificationIcon = actionBar.getCustomView().findViewById(R.id.notificationIcon);
            TextView badgeText = actionBar.getCustomView().findViewById(R.id.badgeText);
            badgeText.setVisibility(View.VISIBLE);

            notificationIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(AdminHome.this, Notifications.class);
                    startActivity(i);
                }
            });

            int notificationCount = db.getNotificationsList().size();
            updateBadge(badgeText, notificationCount);
        }



        return true;
    }

    private void updateBadge(TextView badgeText, int count) {
        if (count > 0) {
            badgeText.setText(String.valueOf(count));
        } else {
            badgeText.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.profile) {
            Intent i = new Intent(getApplicationContext(), UserProfile.class);
            startActivity(i);
            return true;
        } else if (itemId == R.id.signOutMenu) {
            SharedPreferences preferences = getSharedPreferences("userDetails", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        int itemId = item.getItemId();
        if (itemId == R.id.navigation_jobs) {
            Intent intent  = new Intent(getApplicationContext(), ViewJobsWithStatus.class);
            startActivity(intent);
        }
        return true;
    };
}