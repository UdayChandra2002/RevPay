package com.revpay.service;

import com.revpay.dao.UserDAO;
import com.revpay.model.Notification;
import com.revpay.model.User;
import com.revpay.util.PasswordUtil;

public class UserService {

    private final UserDAO userDAO = new UserDAO();
    private final NotificationService notificationService = new NotificationService();

    /* ================= GET USER BY ID ================= */

    public User getUserById(int userId) throws Exception {
        return userDAO.getUserById(userId);
    }

    /* ================= GET USER BY EMAIL OR PHONE ================= */

    public User getUserByEmailOrPhone(String value) throws Exception {
        return userDAO.getUserByEmailOrPhone(value);
    }


    public void changePassword(int userId,
                               String currentPassword,
                               String newPassword,
                               String confirmPassword) throws Exception {

        User user = userDAO.getUserById(userId);

        if (!PasswordUtil.verify(currentPassword, user.getPasswordHash())) {
            throw new Exception("Current password is incorrect");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new Exception("Passwords do not match");
        }

        String newHash = PasswordUtil.hash(newPassword);
        userDAO.updatePassword(userId, newHash);

        //  Notification
        Notification n = new Notification();
        n.setUserId(userId);
        n.setMessage("Your login password was changed successfully");
        n.setType("SECURITY");
        notificationService.sendNotification(n);
    }

    public void changeTransactionPin(int userId,
                                     String currentPin,
                                     String newPin,
                                     String confirmPin) throws Exception {

        User user = userDAO.getUserById(userId);

        if (!PasswordUtil.verify(currentPin, user.getTransactionPinHash())) {
            throw new Exception("Current transaction PIN is incorrect");
        }

        if (!newPin.equals(confirmPin)) {
            throw new Exception("PINs do not match");
        }

        String newHash = PasswordUtil.hash(newPin);
        userDAO.updateTransactionPin(userId, newHash);
        user.setTransactionPinHash(newHash);

        // 🔔 Notification
        Notification n = new Notification();
        n.setUserId(userId);
        n.setMessage("Your transaction PIN was updated successfully");
        n.setType("SECURITY");
        notificationService.sendNotification(n);
    }




}
