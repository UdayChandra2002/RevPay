package com.revpay.dao;

import com.revpay.config.DBConnection;
import com.revpay.model.Notification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    /* ================= CREATE NOTIFICATION ================= */

    public void createNotification(Notification n) throws SQLException {

        String sql = """
            INSERT INTO notifications (
                notification_id,
                user_id,
                message,
                type,
                is_read,
                created_at
            )
            VALUES (notify_seq.NEXTVAL, ?, ?, ?, 'N', SYSDATE)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, n.getUserId());
            ps.setString(2, n.getMessage());
            ps.setString(3, n.getType());
            ps.executeUpdate();
        }
    }

    /* ================= GET USER NOTIFICATIONS ================= */

    public List<Notification> getNotificationsByUser(int userId) throws SQLException {

        List<Notification> list = new ArrayList<>();

        String sql = """
            SELECT *
            FROM notifications
            WHERE user_id = ?
            ORDER BY created_at DESC
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Notification n = new Notification();
                n.setNotificationId(rs.getInt("notification_id"));
                n.setUserId(rs.getInt("user_id"));
                n.setMessage(rs.getString("message"));
                n.setType(rs.getString("type"));
                n.setIsRead(rs.getString("is_read"));
                n.setCreatedAt(rs.getDate("created_at"));
                list.add(n);
            }
        }
        return list;
    }

    /* ================= MARK ALL AS READ ================= */

    public void markAllAsRead(int userId) throws SQLException {

        String sql = """
            UPDATE notifications
            SET is_read = 'Y'
            WHERE user_id = ?
              AND is_read = 'N'
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }
}
