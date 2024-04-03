package com.example.growwise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    TextView roleHeading;
    Button btnlogin;
    String value=null;
    DBHelper DB;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                    Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    int userIdFromMethod = DB.checkUserCredentials(user, pass);
                    if (userIdFromMethod != -1) {
                        User userDetails = DB.getUserDetails(userIdFromMethod);
                        saveUserDetails(userDetails.getUserID(), userDetails.getUserName(), userDetails.getDesignation(), userDetails.getPhoneNumber(), userDetails.getDob());
                        if(userDetails.getDesignation().equalsIgnoreCase("supervisor")) {
                            Toast.makeText(MainActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                            Intent i11 = new Intent(getApplicationContext(), SupervisorHome.class);
                            startActivity(i11);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), AdminHome.class);
                            startActivity(i);
                            finish();
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
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