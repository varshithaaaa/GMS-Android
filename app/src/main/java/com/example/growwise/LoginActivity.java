package com.example.growwise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;

    TextView roleHeading;
    Button btnlogin;
    DBHelper DB;
    String value=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle extras = getIntent().getExtras();

        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        btnlogin = (Button) findViewById(R.id.btnsignin1);
        DB = new DBHelper(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("")||pass.equals(""))
                    Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    int userIdFromMethod = DB.checkUserCredentials(user, pass);
                    if (userIdFromMethod != -1) {
                        User userDetails = DB.getUserDetails(userIdFromMethod);
                        Log.d("designation", userDetails.getDesignation());
                        saveUserDetails(userDetails.getUserID(), userDetails.getUserName(), userDetails.getDesignation(), userDetails.getPhoneNumber(), userDetails.getDob());
                        Toast.makeText(LoginActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), AdminHome.class);
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void saveUserDetails(int userId, String username, String designation, String phoneNumber, String dob) {
        SharedPreferences sharedPreferences = getSharedPreferences("userDetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("userId", userId);
        editor.putString("username", username);
        editor.putString("designation", designation);
        editor.putString("DOB", dob);
        editor.putString("phoneNumber", phoneNumber);
        editor.apply();
    }
}