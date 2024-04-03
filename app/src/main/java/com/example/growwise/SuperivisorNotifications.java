// SuperivisorNotifications.java

package com.example.growwise;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SuperivisorNotifications extends AppCompatActivity {

    private RecyclerView recyclerViewNotifications;
    private SupervisorNotificationAdapter notificationAdapter;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superivisor_notifications);

        recyclerViewNotifications = findViewById(R.id.recyclerViewNotifications);
        dbHelper = new DBHelper(getApplicationContext());
        SharedPreferences preferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        int currentUserId = preferences.getInt("userId", -1);
        List<SupervisorNotificationModel> notificationList = dbHelper.getSupervisorNotifications(currentUserId);

        notificationAdapter = new SupervisorNotificationAdapter(this, notificationList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewNotifications.setLayoutManager(layoutManager);
        recyclerViewNotifications.setAdapter(notificationAdapter);
    }
}
