package com.example.growwise;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Contact extends AppCompatActivity {

    Spinner supervisorSpinner;
    EditText msg;

    DBHelper db;
    List<String> supervisorNames;
    List<Integer> supervisorIds;
    Button contactBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        supervisorSpinner = findViewById(R.id.supervisorSpinner);
        msg = findViewById(R.id.msgET);
        contactBtn = findViewById(R.id.contactBtn);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        db = new DBHelper(getApplicationContext());
        populateSupervisorSpinner();

        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRequestToSupervisor();
            }
        });
    }

    private void populateSupervisorSpinner() {
        List<User> supervisors = db.getUsersByDesignation("Supervisor");
        supervisorNames = new ArrayList<>();
        supervisorIds = new ArrayList<>();
        supervisorNames.add("Select supervisor");
        supervisorIds.add(-1);

        for (User supervisor : supervisors) {
            supervisorNames.add(supervisor.getUserName());
            supervisorIds.add(supervisor.getUserID());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, supervisorNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        supervisorSpinner.setAdapter(adapter);
    }

    private void addRequestToSupervisor() {
        String message = msg.getText().toString().trim();

        int supervisor = getSelectedUserId(supervisorSpinner);

        if (supervisor == -1 || message.isEmpty()) {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean success = db.addSupervisorRequest(supervisor, message);
        Intent i2 = getIntent();
        if (success) {
            Toast.makeText(this, "Notification sent!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Contact.this, ViewLocationDetails.class);
            i.putExtras(Objects.requireNonNull(i2.getExtras()));
            startActivity(i);
            finish();
        } else {
            Toast.makeText(this, "Error! Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private int getSelectedUserId(Spinner spinner) {
        int position = spinner.getSelectedItemPosition();
        if (position > 0) {
            return supervisorIds.get(position);
        }
        return -1;
    }
}