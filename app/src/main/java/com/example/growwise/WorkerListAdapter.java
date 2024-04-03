package com.example.growwise;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WorkerListAdapter extends RecyclerView.Adapter<WorkerListAdapter.ViewHolder> {

    private List<User> workerList;
    private boolean[] selectedWorkers;

    public WorkerListAdapter(List<User> workerList) {
        this.workerList = workerList;
        this.selectedWorkers = new boolean[workerList.size()]; // Initialize boolean array
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView workerNameTextView;
        public CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            workerNameTextView = itemView.findViewById(R.id.workerNameTextView);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.worker_list_item, parent, false);
        return new WorkerListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User currentWorker = workerList.get(position);

        holder.workerNameTextView.setText(currentWorker.getUserName());
        holder.checkBox.setChecked(selectedWorkers[position]);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selectedWorkers[position] = isChecked;
            }
        });
    }

    @Override
    public int getItemCount() {
        return workerList.size();
    }

    public List<Integer> getSelectedWorkerIds() {
        List<Integer> selectedWorkerIds = new ArrayList<>();

        for (int i = 0; i < selectedWorkers.length; i++) {
            if (selectedWorkers[i]) {
                selectedWorkerIds.add(workerList.get(i).getUserID());
            }
        }

        return selectedWorkerIds;
    }
}
