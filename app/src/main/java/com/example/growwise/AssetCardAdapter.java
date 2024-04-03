package com.example.growwise;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AssetCardAdapter extends RecyclerView.Adapter<AssetCardAdapter.ViewHolder> {

    private List<Tool> assetList;

    public AssetCardAdapter(List<Tool> assetList) {
        this.assetList = assetList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView assetNameTextView;
        public TextView quantityTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            assetNameTextView = itemView.findViewById(R.id.idTVAssetName);
            quantityTextView = itemView.findViewById(R.id.idTVQuantity);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.asset_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tool currentAsset = assetList.get(position);

        holder.assetNameTextView.setText("Tool: " + currentAsset.getToolName());
        holder.quantityTextView.setText("Quantity: " + String.valueOf(currentAsset.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return assetList.size();
    }
}
