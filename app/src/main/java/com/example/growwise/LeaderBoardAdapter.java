package com.example.growwise;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.LeaderBoardViewHolder> {

    private List<Supervisor> supervisorList;

    public LeaderBoardAdapter(List<Supervisor> supervisorList) {
        this.supervisorList = supervisorList;
    }

    @NonNull
    @Override
    public LeaderBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leader_board_card, parent, false);
        return new LeaderBoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderBoardViewHolder holder, int position) {
        Supervisor supervisor = supervisorList.get(position);
        holder.supervisorNameTextView.setText(supervisor.getSupervisorName());
//        holder.ratingBar.setRating(supervisor.getRating());
    }

    @Override
    public int getItemCount() {
        return supervisorList.size();
    }

    public static class LeaderBoardViewHolder extends RecyclerView.ViewHolder {
        TextView supervisorNameTextView;
        RatingBar ratingBar;

        public LeaderBoardViewHolder(@NonNull View itemView) {
            super(itemView);
            supervisorNameTextView = itemView.findViewById(R.id.supervisorName);
//            ratingBar = itemView.findViewById(R.id.rating);
        }
    }
}

