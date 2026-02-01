package com.revpay.model;

import java.util.Date;

public class MoneyRequest {

    private int requestId;
    private int senderId;
    private int receiverId;
    private double amount;
    private String status;
    private Date expiryDate;
    private String rejectionReason;
    private Date createdAt;

    public MoneyRequest() {}

    public MoneyRequest(int requestId, int senderId, int receiverId,
                        double amount, String status, Date expiryDate,
                        String rejectionReason, Date createdAt) {
        this.requestId = requestId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.status = status;
        this.expiryDate = expiryDate;
        this.rejectionReason = rejectionReason;
        this.createdAt = createdAt;
    }

    public int getRequestId() { return requestId; }
    public void setRequestId(int requestId) { this.requestId = requestId; }

    public int getSenderId() { return senderId; }
    public void setSenderId(int senderId) { this.senderId = senderId; }

    public int getReceiverId() { return receiverId; }
    public void setReceiverId(int receiverId) { this.receiverId = receiverId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
