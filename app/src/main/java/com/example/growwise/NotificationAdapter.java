package com.example.growwise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// NotificationAdapter.java
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Request> requestList;
    private Context context;

    public NotificationAdapter(Context context, List<Request> requestList) {
        this.context = context;
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_card, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Request request = requestList.get(position);

        // Set data to the CardView
        holder.notificationText.setText(String.format("%d %s from %s", request.getQuantity(), request.getToolName(), request.getSupervisorName()));

        // Set onClickListeners for buttons
        holder.completedIssueBtn.setOnClickListener(v -> handleButtonClick(request, 1, holder));
        holder.cancelledissueBtn.setOnClickListener(v -> handleButtonClick(request, 0, holder));
        if (request.isAccepted() == 1) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.main));
            holder.completedIssueBtn.setVisibility(View.GONE);
            holder.cancelledissueBtn.setVisibility(View.GONE);
        } else if(request.isAccepted() == 0) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, com.google.android.material.R.color.design_default_color_error));
            holder.completedIssueBtn.setVisibility(View.GONE);
            holder.cancelledissueBtn.setVisibility(View.GONE);
        }else if(request.isAccepted() == -1){
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
            holder.completedIssueBtn.setVisibility(View.VISIBLE);
            holder.cancelledissueBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView notificationText;
        AppCompatButton completedIssueBtn;
        AppCompatButton cancelledissueBtn;

        NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            notificationText = itemView.findViewById(R.id.notificationText);
            completedIssueBtn = itemView.findViewById(R.id.completedIssueBtn);
            cancelledissueBtn = itemView.findViewById(R.id.cancelledissueBtn);
        }
    }

    // Method to handle button clicks
    private void handleButtonClick(Request request, int accepted, NotificationViewHolder holder) {

        boolean updated = updateRequest(request.getRequestId(), accepted);
        if (updated) {
            if(accepted == 1) {
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.main));
                holder.completedIssueBtn.setVisibility(View.GONE);
                holder.cancelledissueBtn.setVisibility(View.GONE);
            } else {
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, com.google.android.material.R.color.design_default_color_error));
                holder.completedIssueBtn.setVisibility(View.GONE);
                holder.cancelledissueBtn.setVisibility(View.GONE);
            }
            Toast.makeText(context, "Status Updated!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error! Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to update the toolsRequested table
    private boolean updateRequest(int requestId, int accepted) {
        DBHelper dbHelper = new DBHelper(context);
        boolean updated = dbHelper.updateRequest(requestId, accepted);
        dbHelper.close();
        return updated;
    }
}
