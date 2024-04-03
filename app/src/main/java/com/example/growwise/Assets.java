package com.example.growwise;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Assets extends AppCompatActivity {

    private DBHelper db;
    Button requestBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets);

        db = new DBHelper(getApplicationContext());

        Intent intent = getIntent();
        requestBtn = findViewById(R.id.requestAssetBtn);

        List<Tool> assetList = getTools();

        RecyclerView recyclerView = findViewById(R.id.idRVAssetCards);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        AssetCardAdapter adapter = new AssetCardAdapter(assetList);
        recyclerView.setAdapter(adapter);

        Button allotAssetBtn = findViewById(R.id.allotAssetBtn);
        allotAssetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Assets.this, AddAllotAsset.class);
                intent.putExtra("from", "Allot");
                startActivity(intent);
            }
        });

        Button addToolBtn = findViewById(R.id.addToolBtn);
        addToolBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Assets.this, AddAllotAsset.class);
                intent.putExtra("from", "Add");
                startActivity(intent);
            }
        });

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Assets.this, AddAllotAsset.class);
                intent.putExtra("from", "Request");
                startActivity(intent);
            }
        });

        SharedPreferences preferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String userDesignation = preferences.getString("designation", "Manager");

        if(userDesignation.equalsIgnoreCase("Manager")) {
            requestBtn.setVisibility(View.GONE);
        } else if(userDesignation.equalsIgnoreCase("Supervisor")) {
            addToolBtn.setVisibility(View.GONE);
            allotAssetBtn.setVisibility(View.GONE);
        }

        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private List<Tool> getTools() {
        List<Tool> tools = new ArrayList<>();
        tools = db.getAllTools();

        return tools;
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
