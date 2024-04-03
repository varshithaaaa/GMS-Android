package com.example.growwise;

// Worker.java
public class Worker {
    private int id;
    private String name;
    private boolean isSelected;

    public Worker(int id, String name) {
        this.id = id;
        this.name = name;
        this.isSelected = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

