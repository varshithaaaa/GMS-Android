package com.example.growwise;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Notifications extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;
    private List<Request> requestList;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        // Initialize the ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        db = new DBHelper(this);

        // Initialize the RecyclerView and its adapter
        recyclerView = findViewById(R.id.recyclerViewNotifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Assuming you have a method to retrieve the list of notifications
        requestList = getNotificationList();

        // Initialize the adapter with the list of requests
        notificationAdapter = new NotificationAdapter(this, requestList);

        // Set the adapter for the RecyclerView
        recyclerView.setAdapter(notificationAdapter);
    }

    private List<Request> getNotificationList() {
        List<Request> notificationList = db.getNotificationsList();
        return notificationList;
    }
}
