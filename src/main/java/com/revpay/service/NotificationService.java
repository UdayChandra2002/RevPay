package com.revpay.service;

import com.revpay.dao.NotificationDAO;
import com.revpay.model.Notification;

import java.util.List;

public class NotificationService {

    private final NotificationDAO notificationDAO = new NotificationDAO();

    /* ================= SEND NOTIFICATION ================= */

    public void sendNotification(Notification notification) throws Exception {
        notificationDAO.createNotification(notification);
    }

    /* ================= VIEW NOTIFICATIONS ================= */

    public List<Notification> getUserNotifications(int userId) throws Exception {
        return notificationDAO.getNotificationsByUser(userId);
    }

    /* ================= MARK AS READ ================= */

    public void markAllAsRead(int userId) throws Exception {
        notificationDAO.markAllAsRead(userId);
    }
}
