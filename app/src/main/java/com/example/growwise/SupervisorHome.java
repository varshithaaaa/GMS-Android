package com.example.growwise;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.growwise.databinding.ActivitySupervisorHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SupervisorHome extends AppCompatActivity {

    private TextView dateTextView;
    private TextView timeTextView;
    private ActivitySupervisorHomeBinding binding;

    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySupervisorHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home)
                .build();


        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        dateTextView = findViewById(R.id.date);
        timeTextView = findViewById(R.id.time);

        db = new DBHelper(this);

        setDate();
        updateTime();
    }

    private void updateTime() {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String time = timeFormat.format(calendar.getTime());
        timeTextView.setText(time);
    }
    private void setDate() {
        Date currentDate = Calendar.getInstance().getTime();
        String formattedDate = DateFormat.format("MMMM dd, yyyy", currentDate).toString();
        dateTextView.setText(formattedDate);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        int itemId = item.getItemId();
        if (itemId == R.id.navigation_assign_task) {
            Intent intent  = new Intent(getApplicationContext(), AssignTask.class);
            startActivity(intent);
            finish();
        } else if(itemId == R.id.navigation_tools) {
            Intent intent  = new Intent(getApplicationContext(), Assets.class);
            intent.putExtra("from", "SupervisorHome");
            startActivity(intent);
            finish();
        } else if(itemId == R.id.navigation_leaderBoard) {
            Intent intent  = new Intent(getApplicationContext(), LeaderBoard.class);
            startActivity(intent);
            finish();
        }
        return true;
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.supervisor_main_menu, menu);

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
                Intent i = new Intent(SupervisorHome.this, SuperivisorNotifications.class);
                startActivity(i);
            }
        });

        SharedPreferences preferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        int currentUserId = preferences.getInt("userId", -1);
        List<SupervisorNotificationModel> notificationList = db.getSupervisorNotifications(currentUserId);
        int notificationCount = notificationList.size();
        updateBadge(badgeText, notificationCount);
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
        } else if (itemId == R.id.workers) {
            Intent intent = new Intent(getApplicationContext(), EmployeesList.class);
            startActivity(intent);
            return true;
        } else if(itemId == R.id.jobs) {
            Intent intent = new Intent(getApplicationContext(), ViewJobsWithStatus.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
