package com.example.growwise;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class AddAllotAsset extends AppCompatActivity {
    private TextView toolNameTv;
    private EditText toolNameET;
    private EditText quantityET;

    private TextView allocateToolTV, supervisorTv;
    private Spinner allocateToolSpinner, supervisorSpinner;
    private Button requestBtn;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_allot_asset);
        db = new DBHelper(getApplicationContext());
        toolNameTv = findViewById(R.id.toolNameTV);
        toolNameET = findViewById(R.id.toolNameET);
        quantityET = findViewById(R.id.quantityET);

        allocateToolTV = findViewById(R.id.allocateToolTV);
        allocateToolSpinner = findViewById(R.id.allocateToolSpinner);

        supervisorTv = findViewById(R.id.supervisorTV);
        supervisorSpinner = findViewById(R.id.supervisorSpinner);

        requestBtn = findViewById(R.id.requestBtn);
        populateSupervisorSpinner();
        populateAllocateToolSpinner();

        Bundle extras = getIntent().getExtras();
        Button addBtn = findViewById(R.id.addBtn);
        if (extras != null && extras.containsKey("from")) {
            String from = extras.getString("from");
            if ("Allot".equals(from)) {
                toolNameTv.setVisibility(View.GONE);
                toolNameET.setVisibility(View.GONE);
                requestBtn.setVisibility(View.GONE);
            }

            if ("Add".equals(from)) {
                supervisorTv.setVisibility(View.GONE);
                supervisorSpinner.setVisibility(View.GONE);
                allocateToolSpinner.setVisibility(View.GONE);
                allocateToolTV.setVisibility(View.GONE);
                requestBtn.setVisibility(View.GONE);
            }

            if ("Request".equals(from)) {
                supervisorTv.setVisibility(View.GONE);
                supervisorSpinner.setVisibility(View.GONE);
                toolNameTv.setVisibility(View.GONE);
                toolNameET.setVisibility(View.GONE);
                addBtn.setVisibility(View.GONE);
            }

        }

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int toolId = getSelectedToolId(allocateToolSpinner);
                String quantityStr = quantityET.getText().toString().trim();

                if(allocateToolSpinner.getSelectedItem().toString().equalsIgnoreCase("Please select a tool") || quantityStr.isEmpty()) {
                    Toast.makeText(AddAllotAsset.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else if(!quantityStr.matches("\\d+") && Integer.parseInt(quantityStr) > 0) {
                    Toast.makeText(AddAllotAsset.this, "Quantity should be more than 0", Toast.LENGTH_SHORT).show();
                }
                else {
                    int quantity = Integer.parseInt(quantityStr);
                    DBHelper dbHelper = new DBHelper(AddAllotAsset.this);

                    SharedPreferences preferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
                    int userId = preferences.getInt("userId", -1);

                    boolean inserted = dbHelper.insertRequest(toolId, userId, Integer.parseInt(quantityStr), -1);
                    if(inserted) {
                        Toast.makeText(AddAllotAsset.this, "Tool requested", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), Assets.class);
                        i.putExtra("from", "SupervisorHome");
                        startActivity(i);
                    } else {
                        Toast.makeText(AddAllotAsset.this, "Error! Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String from = extras.getString("from");
                String toolName = toolNameET.getText().toString().trim();
                String quantityStr = quantityET.getText().toString().trim();

                if ("Add".equals(from)) {
                    if(toolName.isEmpty() || quantityStr.isEmpty()) {
                        Toast.makeText(AddAllotAsset.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    } else if(!quantityStr.matches("\\d+") && Integer.parseInt(quantityStr) > 0) {
                        Toast.makeText(AddAllotAsset.this, "Quantity should be more than 0", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        int quantity = Integer.parseInt(quantityStr);

                        DBHelper dbHelper = new DBHelper(AddAllotAsset.this);
                        boolean inserted = dbHelper.insertTool(toolName, quantity);
                        dbHelper.close();

                        if(inserted) {
                            Toast.makeText(AddAllotAsset.this, "Asset Added!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), Assets.class);
                            startActivity(i);
                            finish();
                        }  else {
                            Toast.makeText(AddAllotAsset.this, "Error! Please try again.", Toast.LENGTH_SHORT).show();
                        }

                        finish();
                    }
                } else if ("Allot".equals(from)) {
                    if (validateAllotment()) {
                        int supervisorId = getSelectedUserId(supervisorSpinner);
                        int toolId = getSelectedToolId(allocateToolSpinner);
                        int quantity = Integer.parseInt(quantityET.getText().toString());

                        boolean isInsertSuccessful = db.insertToolsAllocated(toolId, supervisorId, quantity);

                        if (isInsertSuccessful) {
                            boolean isUpdateSuccessful = db.updateToolsQuantity(toolId, quantity);

                            if (isUpdateSuccessful) {
                                Toast.makeText(AddAllotAsset.this, "Allocation successful", Toast.LENGTH_SHORT).show();
                                db.close();
                                Intent intent = new Intent(AddAllotAsset.this, Assets.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(AddAllotAsset.this, "Update failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AddAllotAsset.this, "Insertion failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void populateSupervisorSpinner() {
        List<User> supervisors = db.getUsersByDesignation("Supervisor");
        List<String> supervisorNames = new ArrayList<>();
        supervisorNames.add("Select supervisor");

        for (User supervisor : supervisors) {
            supervisorNames.add(supervisor.getUserName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, supervisorNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        supervisorSpinner.setAdapter(adapter);
    }

    private void populateAllocateToolSpinner() {
        List<Tool> tools = db.getAllTools();
        List<String> toolNames = new ArrayList<>();
        toolNames.add("Select tool");

        for (Tool tool : tools) {
            toolNames.add(tool.getToolName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, toolNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        allocateToolSpinner.setAdapter(adapter);
    }

    private boolean validateAllotment() {
        // Validate spinners
        if (supervisorSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select a supervisor", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (allocateToolSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select a tool", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate quantity
        String quantityStr = quantityET.getText().toString().trim();
        if (quantityStr.isEmpty() || !quantityStr.matches("\\d+") || Integer.parseInt(quantityStr) <= 0) {
            Toast.makeText(this, "Please enter a valid quantity", Toast.LENGTH_SHORT).show();
            return false;
        }
        int toolId = getSelectedToolId(allocateToolSpinner);
        int quantity = Integer.parseInt(quantityStr);

        if (quantity > db.getToolQuantity(toolId)) {
            Toast.makeText(this, "Quantity not enough", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private int getSelectedUserId(Spinner spinner) {
        int position = spinner.getSelectedItemPosition();
        if (position > 0) {
            List<User> supervisors = db.getUsersByDesignation("Supervisor");
            return supervisors.get(position - 1).getUserID();
        }
        return -1;
    }

    private int getSelectedToolId(Spinner spinner) {
        int position = spinner.getSelectedItemPosition();
        if (position > 0) {
            List<Tool> tools = db.getAllTools();
            return tools.get(position - 1).getToolId();
        }
        return -1;
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