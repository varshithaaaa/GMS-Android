package com.example.growwise;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WorkerViewHolder extends RecyclerView.ViewHolder {
    public TextView workerNameTextView;
    public CheckBox checkBox;

    public WorkerViewHolder(@NonNull View itemView) {
        super(itemView);
        workerNameTextView = itemView.findViewById(R.id.workerNameTextView);
        checkBox = itemView.findViewById(R.id.checkBox);
    }
}