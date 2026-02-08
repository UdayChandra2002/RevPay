package com.revpay.menu;

import com.revpay.main.RevPayApplication;
import com.revpay.model.Notification;
import com.revpay.model.User;
import com.revpay.service.NotificationService;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NotificationMenu {
    private static final Logger logger =
            LogManager.getLogger(NotificationMenu.class);
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

            logger.info("===== NOTIFICATIONS =====");

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
