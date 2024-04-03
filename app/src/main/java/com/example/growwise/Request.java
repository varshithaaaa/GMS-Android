package com.example.growwise;
public class Request {
    private int requestId;
    private int toolId;
    private int requestorId;
    private int quantity;
    private int accepted;
    private String toolName;
    private String supervisorName;

    public Request(int requestId, int toolId, int requestorId, int quantity, int accepted) {
        this.requestId = requestId;
        this.toolId = toolId;
        this.requestorId = requestorId;
        this.quantity = quantity;
        this.accepted = accepted;
    }

    public Request(int requestId, int toolId, int requestorId, int quantity, int accepted, String toolName, String supervisorName) {
        this.requestId = requestId;
        this.toolId = toolId;
        this.requestorId = requestorId;
        this.quantity = quantity;
        this.accepted = accepted;
        this.supervisorName = supervisorName;
        this.toolName = toolName;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getToolId() {
        return toolId;
    }

    public void setToolId(int toolId) {
        this.toolId = toolId;
    }

    public int getRequestorId() {
        return requestorId;
    }

    public void setRequestorId(int requestorId) {
        this.requestorId = requestorId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int isAccepted() {
        return accepted;
    }

    public void setAccepted(int accepted) {
        this.accepted = accepted;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }
}
