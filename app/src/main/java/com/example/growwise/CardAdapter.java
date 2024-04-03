package com.example.growwise;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CardModel> selectedBlocks;
    private static ArrayList<CardModel> courseModelArrayList;

    // Constructor
    public CardAdapter(Context context, ArrayList<CardModel> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
        this.selectedBlocks = new ArrayList<CardModel>();
    }

    public ArrayList<CardModel> getSelectedBlocks() {
//        for (int i = 0; i < selectedBlocks.size(); i++) {
//            courseModelArrayList.remove(selectedBlocks.get(i));
//        }
        return selectedBlocks;
    }

    public void resetSelectedBlocks() {
        for (CardModel model : courseModelArrayList) {
            model.setSelected(false);
        }
        selectedBlocks.clear();
        notifyDataSetChanged();
    }
    public void setLong_listener(OnLongClickListener longPressBlock) {
        long_listener = longPressBlock;
    }

    public interface OnItemClickListener {
        void onItemClick(String name);
    }

    public interface OnLongClickListener {
        void onLongClick(String name);
    }

    private OnItemClickListener listener;

    private OnLongClickListener long_listener;

    // Set the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        CardModel model = courseModelArrayList.get(position);
        holder.cardNameTV.setText(model.getcard_name());

        holder.setOnItemClickListener(listener);
        holder.setLongClickListener(long_listener);
        holder.resetViewState();



        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                model.setSelected(!model.isSelected());
                holder.cardView.setCardBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);
                if(model.isSelected()) {
                    selectedBlocks.add(model);
                } else {
                    selectedBlocks.remove(model);
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return courseModelArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final TextView cardNameTV;
        private OnItemClickListener listener;
        private OnLongClickListener long_listener;
        public CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardNameTV = itemView.findViewById(R.id.idTVCardName);

            final Context context = itemView.getContext();
            cardView = (CardView)itemView.findViewById(R.id.card_view);

            cardView.setOnClickListener(this);
            cardView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (listener != null) {
                listener.onItemClick(cardNameTV.getText().toString());
            }
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        public void setLongClickListener(OnLongClickListener listener) {
            this.long_listener = listener;
        }

        @Override
        public boolean onLongClick(View v) {

            if (long_listener != null) {

                long_listener.onLongClick(cardNameTV.getText().toString());
            }
            return true;
        }

        public void resetViewState() {
            cardView.setCardBackgroundColor(Color.WHITE);
        }
    }
}


