package com.example.growwise;

public class AssetCardModel {
    private String assetName;
    private int quantity;

    public AssetCardModel(String assetName, int quantity) {
        this.assetName = assetName;
        this.quantity = quantity;
    }

    public String getAssetName() {
        return assetName;
    }

    public int getQuantity() {
        return quantity;
    }
}
