package com.revpay.menu;

import com.revpay.model.Notification;
import com.revpay.model.User;
import com.revpay.service.NotificationService;

import java.util.List;

public class NotificationMenu {

    private final User user;

    public NotificationMenu(User user) {
        this.user = user;
    }

    public void show() {

        NotificationService service = new NotificationService();

        try {
            List<Notification> list =
                    service.getUserNotifications(user.getUserId());

            if (list.isEmpty()) {
                System.out.println("No notifications.");
                return;
            }

            System.out.println("===== NOTIFICATIONS =====");

            for (Notification n : list) {
                String status = n.getIsRead().equals("N") ? "[NEW]" : "";
                System.out.println(
                        status + " " +
                                n.getMessage() +
                                " (" + n.getType() + ")"
                );
            }

            // Mark all as read after viewing
            service.markAllAsRead(user.getUserId());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
