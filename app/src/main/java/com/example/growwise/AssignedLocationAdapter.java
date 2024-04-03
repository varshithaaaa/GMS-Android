package com.example.growwise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AssignedLocationAdapter extends RecyclerView.Adapter<AssignedLocationAdapter.AssignedLocationViewHolder> {

    private List<AssignedLocationsModel> assignedLocationsList;
    private Context context;

    public AssignedLocationAdapter(Context context, List<AssignedLocationsModel> assignedLocationsList) {
        this.context = context;
        this.assignedLocationsList = assignedLocationsList;
    }

    @NonNull
    @Override
    public AssignedLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.asigned_location_card, parent, false);
        return new AssignedLocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignedLocationViewHolder holder, int position) {
        AssignedLocationsModel assignedLocation = assignedLocationsList.get(position);

        holder.supervisorNameTV.setText("Supervisor: " + assignedLocation.getSupervisorName());
        holder.locationNameTV.setText("Location: " + assignedLocation.getLocationName());

        // Construct a string with worker names
        StringBuilder workersStringBuilder = new StringBuilder("Workers: ");
        for (String worker : assignedLocation.getWorkers()) {
            workersStringBuilder.append(worker).append(", ");
        }
        // Remove the trailing comma and space
        String workersString = workersStringBuilder.toString().replaceAll(", $", "");
        holder.workersTV.setText(workersString);
    }

    @Override
    public int getItemCount() {
        return assignedLocationsList.size();
    }

    static class AssignedLocationViewHolder extends RecyclerView.ViewHolder {
        TextView supervisorNameTV;
        TextView locationNameTV;
        TextView workersTV;

        AssignedLocationViewHolder(@NonNull View itemView) {
            super(itemView);
            supervisorNameTV = itemView.findViewById(R.id.supervisornameTV);
            locationNameTV = itemView.findViewById(R.id.locationNameTV);
            workersTV = itemView.findViewById(R.id.workersTV);
        }
    }
}
