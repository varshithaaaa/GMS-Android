// TaskAdapter.java
package com.example.growwise;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> implements Filterable {

    private List<Task> taskList;
    private final List<Task> taskListFull;
    private static OnJobCardClickListener onJobCardClickListener;

    public TaskAdapter(List<Task> taskList, OnJobCardClickListener listener) {
        this.taskList = taskList;
        this.taskListFull = new ArrayList<Task>(taskList);
        this.onJobCardClickListener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_card, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public interface OnJobCardClickListener {
        void onJobCardClick(int position);
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView jobIdTextView, jobTypeTextView, managerTextView, dateTextView, startTimeTextView, endTimeTextView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            jobIdTextView = itemView.findViewById(R.id.jobIdTextView);
            jobTypeTextView = itemView.findViewById(R.id.jobTypeTextView);
            managerTextView = itemView.findViewById(R.id.managerTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            startTimeTextView = itemView.findViewById(R.id.startTimeTextView);
            endTimeTextView = itemView.findViewById(R.id.endTimeTextView);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (onJobCardClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onJobCardClickListener.onJobCardClick(position);
                }
            }
        }
        public void bind(Task task) {
            jobIdTextView.setText("Job Id: " + task.getJobId());
            jobTypeTextView.setText("Job Type: " + task.getJobType());
            managerTextView.setText("Manager Incharge: " + task.getManagerIncharge());
            dateTextView.setText("Date: " + task.getDate());
            startTimeTextView.setText("Start Time: " + task.getStartTime());
        }
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return taskList.get(position).getJobId();
    }


    @Override
    public Filter getFilter() {
        Log.d("Filter", "getFilter() called");
        return taskFilter;
    }

    private Filter taskFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.d("Filter", "performFiltering() called with constraint: " + constraint);

            List<Task> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(taskListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Task task : taskListFull) {
                    if (String.valueOf(task.getJobId()).toLowerCase().contains(filterPattern) ||
                            task.getJobType().toLowerCase().contains(filterPattern) ||
                            task.getManagerIncharge().toLowerCase().contains(filterPattern)) {
                        filteredList.add(task);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            taskList.clear();
            taskList.addAll((List<Task>) results.values);
            notifyDataSetChanged();
        }
    };
}
