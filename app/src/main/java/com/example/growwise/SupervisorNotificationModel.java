package com.example.growwise;

public class SupervisorNotificationModel {

    private int supervisorRequestId;
    private int requestToUserId;
    private String message;

    public SupervisorNotificationModel(int supervisorRequestId, int requestToUserId, String message) {
        this.supervisorRequestId = supervisorRequestId;
        this.requestToUserId = requestToUserId;
        this.message = message;
    }

    public int getSupervisorRequestId() {
        return supervisorRequestId;
    }

    public int getRequestToUserId() {
        return requestToUserId;
    }

    public String getMessage() {
        return message;
    }
}
