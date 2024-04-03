package com.example.growwise;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewEmployee extends AppCompatActivity {

    private ImageView imageView;
    private EditText usernameET;
    private EditText dobEditText;
    private Spinner roleSpinner;
    private EditText mobileNumberEditText;

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private DBHelper databaseHelper;
    Uri selectedImageUri = null;

    private Calendar calendar;
    private static final int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_employee);
        dobEditText = findViewById(R.id.complaintPhoneNumberET);
        roleSpinner = findViewById(R.id.complaintBlockSpinner);
        mobileNumberEditText = findViewById(R.id.issueDescET);
        Button addNewEmployeeButton = findViewById(R.id.addNewEmployee);
        usernameET = findViewById(R.id.nameET);
        calendar = Calendar.getInstance();

        databaseHelper = new DBHelper(this);

        addNewEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserToDatabase();
            }
        });

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

        dobEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(
                        NewEmployee.this,
                        dateSetListener,
                        year, month, dayOfMonth
                ).show();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void updateDate() {
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        dobEditText.setText(sdf.format(calendar.getTime()));
    }

    private void addUserToDatabase() {
        String imagePath = null;
        String username = usernameET.getText().toString();
        String dob = dobEditText.getText().toString();
        String role = roleSpinner.getSelectedItem().toString();
        String mobileNumber = mobileNumberEditText.getText().toString();

        if (username.isEmpty() || dob.isEmpty() || role.isEmpty() || mobileNumber.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User();
        if(selectedImageUri != null) {
            user.setImagePath(imageUriToString(selectedImageUri));
        } else {
            user.setImagePath(null);
        }
        user.setUserName(username);
        user.setDob(dob);
        user.setDesignation(role);
        user.setPhoneNumber(mobileNumber);
        user.setActive(true);

        long result = databaseHelper.addUser(user);

        if (result != -1) {
            Toast.makeText(NewEmployee.this, "Employee Added", Toast.LENGTH_SHORT).show();
            Intent intent  = new Intent(getApplicationContext(), EmployeesList.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error! Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private String imageUriToString(Uri uri) {
        return uri.toString();
    }
}