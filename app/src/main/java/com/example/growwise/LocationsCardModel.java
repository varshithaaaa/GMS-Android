package com.example.growwise;

import java.util.ArrayList;

public class LocationsCardModel {

    private int locationID;
    private String locationName;
    private String managerIncharge;
    private String supervisorIncharge;
    private int noOfWorkers;
    private String phoneNumber;
    private ArrayList<String> workers;

    public ArrayList<String> getWorkers() {
        return workers;
    }

    public void setWorkers(ArrayList<String> workers) {
        this.workers = workers;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getManagerIncharge() {
        return managerIncharge;
    }

    public void setManagerIncharge(String managerIncharge) {
        this.managerIncharge = managerIncharge;
    }

    public String getSupervisorIncharge() {
        return supervisorIncharge;
    }

    public void setSupervisorIncharge(String supervisorIncharge) {
        this.supervisorIncharge = supervisorIncharge;
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
}
