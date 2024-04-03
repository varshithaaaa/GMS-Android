package com.example.growwise;

public class Supervisor {

    private String supervisorName;
    private float rating;

    public Supervisor(String supervisorName, float rating) {
        this.supervisorName = supervisorName;
        this.rating = rating;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public float getRating() {
        return rating;
    }
}

