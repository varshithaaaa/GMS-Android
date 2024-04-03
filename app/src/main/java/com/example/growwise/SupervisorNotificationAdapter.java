// SupervisorNotificationAdapter.java

package com.example.growwise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SupervisorNotificationAdapter extends RecyclerView.Adapter<SupervisorNotificationAdapter.SupervisorNotificationViewHolder> {

    private List<SupervisorNotificationModel> notificationList;
    private Context context;

    public SupervisorNotificationAdapter(Context context, List<SupervisorNotificationModel> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public SupervisorNotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supervisor_notification_card, parent, false);
        return new SupervisorNotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SupervisorNotificationViewHolder holder, int position) {
        SupervisorNotificationModel notification = notificationList.get(position);

        // Set data to the CardView
        holder.notificationText.setText(notification.getMessage());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    static class SupervisorNotificationViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView notificationText;

        SupervisorNotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            notificationText = itemView.findViewById(R.id.notificationText);
        }
    }
}
