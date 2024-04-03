// Task.java
package com.example.growwise;

public class Task {
    private int jobId;
    private String jobType;
    private String managerIncharge;
    private String date;
    private String startTime;
    private String endTime;
    private String supervisorIncharge;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSupervisorIncharge() {
        return supervisorIncharge;
    }

    public void setSupervisorIncharge(String supervisorIncharge) {
        this.supervisorIncharge = supervisorIncharge;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public void setManagerIncharge(String managerIncharge) {
        this.managerIncharge = managerIncharge;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getJobId() {
        return jobId;
    }

    public String getJobType() {
        return jobType;
    }

    public String getManagerIncharge() {
        return managerIncharge;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
