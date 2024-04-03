package com.example.growwise;

import java.util.ArrayList;

public class AssignedLocationsModel {
    private int locationId;
    private String locationName;
    private int managerInchargeId;
    private int supervisorInchargeId;
    private int noOfWorkers;
    private String phoneNumber;
    private ArrayList<String> workers;  // ArrayList to store worker names
    private String supervisorName;

    // Constructors, getters, and setters...

    // Constructor
    public AssignedLocationsModel(int locationId, String locationName, int managerInchargeId, int supervisorInchargeId, int noOfWorkers, String phoneNumber, ArrayList<String> workers, String supervisorName) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.managerInchargeId = managerInchargeId;
        this.supervisorInchargeId = supervisorInchargeId;
        this.noOfWorkers = noOfWorkers;
        this.phoneNumber = phoneNumber;
        this.workers = workers;
        this.supervisorName = supervisorName;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getManagerInchargeId() {
        return managerInchargeId;
    }

    public void setManagerInchargeId(int managerInchargeId) {
        this.managerInchargeId = managerInchargeId;
    }

    public int getSupervisorInchargeId() {
        return supervisorInchargeId;
    }

    public void setSupervisorInchargeId(int supervisorInchargeId) {
        this.supervisorInchargeId = supervisorInchargeId;
    }

    public int getNoOfWorkers() {
        return noOfWorkers;
    }

    public void setNoOfWorkers(int noOfWorkers) {
        this.noOfWorkers = noOfWorkers;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ArrayList<String> getWorkers() {
        return workers;
    }

    public void setWorkers(ArrayList<String> workers) {
        this.workers = workers;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }
}
