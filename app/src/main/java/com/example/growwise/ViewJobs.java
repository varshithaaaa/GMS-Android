package com.example.growwise;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.SearchView;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.growwise.databinding.ActivityViewJobsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ViewJobs extends AppCompatActivity implements TaskAdapter.OnJobCardClickListener{

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private SearchView searchView;
    private ActivityViewJobsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewJobsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home)
                .build();


        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        recyclerView = findViewById(R.id.idTaskCards);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskList = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(this);
        taskList = dbHelper.getAllTasks();
        taskAdapter = new TaskAdapter(taskList, this);
        recyclerView.setAdapter(taskAdapter);

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                taskAdapter.getFilter().filter(newText);
                return true;
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onJobCardClick(int position) {
        Task clickedTask = taskList.get(position);

        Intent intent = new Intent(this, ViewjobDetails.class);
        intent.putExtra("jobId", clickedTask.getJobId());
        intent.putExtra("jobType", clickedTask.getJobType());
        intent.putExtra("managerIncharge", clickedTask.getManagerIncharge());
        intent.putExtra("date", clickedTask.getDate());
        intent.putExtra("startTime", clickedTask.getStartTime());
        intent.putExtra("endTime", clickedTask.getEndTime());
        intent.putExtra("supervisorIncharge", clickedTask.getSupervisorIncharge());

        startActivity(intent);
    }


    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        int itemId = item.getItemId();
        if (itemId == R.id.navigation_home) {
            SharedPreferences preferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
            String userDesignation = preferences.getString("designation", "Manager");
            if(userDesignation.equals("Manager")) {
                Intent intent = new Intent(getApplicationContext(), SupervisorHome.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent1 = new Intent(getApplicationContext(), AdminHome.class);
                startActivity(intent1);
                finish();
            }
        }
        return true;
    };
}
