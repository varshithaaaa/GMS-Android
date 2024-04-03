package com.example.growwise;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocationsCardAdapter extends RecyclerView.Adapter<LocationsCardAdapter.ViewHolder>{

    private final Context context;
    private static ArrayList<LocationsCardModel> courseModelArrayList;

    public CardView cardView;
    private DBHelper db;

    public LocationsCardAdapter(Context context, ArrayList<LocationsCardModel> courseModelArrayList) {
        this.context = context;
        LocationsCardAdapter.courseModelArrayList = courseModelArrayList;
    }
    @NonNull
    @Override
    public LocationsCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_card, parent, false);
        return new LocationsCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationsCardAdapter.ViewHolder holder, int position) {
        LocationsCardModel model = courseModelArrayList.get(position);
        holder.cardNameTV.setText(model.getLocationName());

        holder.setOnItemClickListener(listener);

        holder.cardNameTV.setText(model.getLocationName());

        holder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(LocationsCardModel l) {
                if (listener != null) {
                    listener.onItemClick(l);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseModelArrayList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(LocationsCardModel b);
    }

    private LocationsCardAdapter.OnItemClickListener listener;

    // Set the listener
    public void setOnItemClickListener(LocationsCardAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private LocationsCardAdapter.OnItemClickListener listener;
        private final TextView cardNameTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardNameTV = itemView.findViewById(R.id.idTVCardName);
            final Context context = itemView.getContext();
            cardView = (CardView)itemView.findViewById(R.id.card_view);

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(courseModelArrayList.get(position));
                }
            }
        }

        public void setOnItemClickListener(LocationsCardAdapter.OnItemClickListener listener) {
            this.listener = listener;
        }
    }
}
