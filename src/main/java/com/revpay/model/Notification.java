package com.revpay.model;

import java.util.Date;

public class Notification {

    private int notificationId;
    private int userId;
    private String message;
    private String type;
    private String isRead;
    private Date createdAt;

    public Notification() {}

    public Notification(int notificationId, int userId, String message,
                        String type, String isRead, Date createdAt) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.message = message;
        this.type = type;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public int getNotificationId() { return notificationId; }
    public void setNotificationId(int notificationId) { this.notificationId = notificationId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getIsRead() { return isRead; }
    public void setIsRead(String isRead) { this.isRead = isRead; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
