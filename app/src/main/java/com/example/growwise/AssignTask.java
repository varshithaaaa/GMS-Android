package com.example.growwise;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AssignTask extends AppCompatActivity {
    private DBHelper db;
    Button assigntask;
    EditText datePicker;
    EditText timePicker;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_task);
        db = new DBHelper(getApplicationContext());
        assigntask = findViewById(R.id.assignTask);
        datePicker = findViewById(R.id.startDate);
        timePicker = findViewById(R.id.timePicked);
        List<User> workerList = db.getWorkers();

        RecyclerView recyclerView = findViewById(R.id.selectableNamesRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        WorkerListAdapter adapter = new WorkerListAdapter(workerList);
        recyclerView.setAdapter(adapter);
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(
                        AssignTask.this,
                        dateSetListener,
                        year, month, dayOfMonth
                ).show();
            }
        });

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minutes) {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minutes);
                updateTime();
            }
        };

        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(
                        AssignTask.this,
                        timeSetListener,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false
                ).show();
            }
        });

        assigntask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> selectedWorkerIds = adapter.getSelectedWorkerIds();
                String taskName = ((Spinner) findViewById(R.id.localitySpinner)).getSelectedItem().toString();
                String date = datePicker.getText().toString();
                String startTime = timePicker.getText().toString();
                String workerIdsString = TextUtils.join(",", selectedWorkerIds);

                SharedPreferences preferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
                int userId = preferences.getInt("userId", -1);

                if (taskName.equalsIgnoreCase("Select a task") || date.equalsIgnoreCase("") || startTime.equalsIgnoreCase("") || workerIdsString.equalsIgnoreCase("")) {
                    Toast.makeText(AssignTask.this, "Please fill all the fields.", Toast.LENGTH_SHORT).show();
                } else {
                    boolean inserted = db.insertTask(taskName, userId, date, startTime, workerIdsString, "Upcoming");
                    if (inserted) {
                        Toast.makeText(getApplicationContext(), "Task Assigned!", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), SupervisorHome.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error! Please try again.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void updateDate() {
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        datePicker.setText(sdf.format(calendar.getTime()));
    }

    private void updateTime() {
        String timeFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.US);
        timePicker.setText(sdf.format(calendar.getTime()));
    }

}