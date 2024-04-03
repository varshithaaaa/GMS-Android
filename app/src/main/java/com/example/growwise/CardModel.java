package com.example.growwise;

public class CardModel {

    private String card_name;

    private boolean isSelected = false;

    // Constructor
    public CardModel(String card_name) {
        this.card_name = card_name;
    }

    // Getter and Setter
    public String getcard_name() {
        return card_name;
    }

    public void setcard_name(String card_name) {
        this.card_name = card_name;
    }


    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public boolean isSelected() {
        return isSelected;
    }
}
