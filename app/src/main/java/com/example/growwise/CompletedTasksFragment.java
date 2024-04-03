package com.example.growwise;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class CompletedTasksFragment extends Fragment implements TaskAdapter.OnJobCardClickListener {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;

    List<Task> completedTasks;

    public CompletedTasksFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completed_tasks, container, false);

        recyclerView = view.findViewById(R.id.completedTasksRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DBHelper dbHelper = new DBHelper(getContext());
        completedTasks = dbHelper.getTasksByStatus("Completed");

        taskAdapter = new TaskAdapter(completedTasks, CompletedTasksFragment.this);
        recyclerView.setAdapter(taskAdapter);

        BottomNavigationView bottomNav = view.findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        return view;
    }

    @Override
    public void onJobCardClick(int position) {
        Task clickedTask = completedTasks.get(position);

        Intent intent = new Intent(getContext(), ViewjobDetails.class);
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
            Intent intent  = new Intent(getContext(), AdminHome.class);
            startActivity(intent);
        }
        return true;
    };
}


