package com.example.growwise;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Rating extends AppCompatActivity {

    TextView supervisorName;
    RatingBar ratingBar;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        supervisorName = findViewById(R.id.supervisorName);
        ratingBar = findViewById(R.id.rating);
        submitBtn = findViewById(R.id.submitBtn);

        Intent intent = getIntent();
        if (intent != null) {
            supervisorName.setText(intent.getStringExtra("supervisor"));
        }

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float ratingValue = ratingBar.getRating();

                if (ratingValue >= 0.0) {
                    updateRatingInUsersTable(supervisorName.getText().toString(), ratingValue);
                } else {
                    Toast.makeText(Rating.this, "Invalid rating value!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void updateRatingInUsersTable(String supervisorName, float ratingValue) {
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        int userId = dbHelper.getUserIdBySupervisorName(supervisorName);

        if (userId != -1) {
            boolean updated = dbHelper.updateUserRating(userId, ratingValue);
            if (updated) {
                Toast.makeText(this, "Rating Submitted successfully", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, ViewJobsWithStatus.class);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(this, "Error! Please try again.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Error! Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
