package com.revpay.dao;

import com.revpay.config.DBConnection;
import com.revpay.model.User;

import java.sql.*;

public class UserDAO {

    /* ================= CREATE USER ================= */

    public int createUser(User user) throws SQLException {

        String sql = """
            INSERT INTO users (
                user_id, full_name, email, phone,
                password_hash, transaction_pin_hash,
                user_type, status
            )
            VALUES (users_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, 'ACTIVE')
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql, new String[]{"user_id"})) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getPasswordHash());
            ps.setString(5, user.getTransactionPinHash());
            ps.setString(6, user.getUserType());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

    /* ================= GET USER BY ID ================= */

    public User getUserById(int userId) throws SQLException {

        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapUser(rs);
            }
        }
        return null;
    }

    /* ================= GET USER BY EMAIL OR PHONE ================= */

    public User getUserByEmailOrPhone(String input) throws SQLException {

        String sql = "SELECT * FROM users WHERE email = ? OR phone = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, input);
            ps.setString(2, input);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapUser(rs);
            }
        }
        return null;
    }

    /* ================= UPDATE FAILED ATTEMPTS ================= */

    public void updateFailedAttempts(int userId, int attempts) throws SQLException {

        String sql = "UPDATE users SET failed_attempts = ? WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, attempts);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    //last login
    public void updateLastLogin(int userId) throws SQLException {

        String sql = """
        UPDATE users
        SET last_login = SYSDATE 
        WHERE user_id = ?
    """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }


    /* ================= LOCK USER ================= */

    public void lockUser(int userId) throws SQLException {

        String sql = "UPDATE users SET is_locked = 'Y' WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }

    /* ================= RESET PASSWORD ================= */

    public void resetPassword(int userId, String newHash) throws SQLException {

        String sql = """
            UPDATE users
            SET password_hash = ?,
                failed_attempts = 0,
                is_locked = 'N'
            WHERE user_id = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newHash);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }




    public void updatePassword(int userId, String hash) throws SQLException {

        String sql = """
        UPDATE users
        SET password_hash = ?
        WHERE user_id = ?
    """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, hash);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }


    public void updateTransactionPin(int userId, String hash) throws SQLException {

        String sql = """
        UPDATE users
        SET transaction_pin_hash = ?
        WHERE user_id = ?
    """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, hash);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }


    /* ================= COMMON USER MAPPER ================= */

    private User mapUser(ResultSet rs) throws SQLException {

        User u = new User();
        u.setUserId(rs.getInt("user_id"));
        u.setFullName(rs.getString("full_name"));
        u.setEmail(rs.getString("email"));
        u.setPhone(rs.getString("phone"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setTransactionPinHash(rs.getString("transaction_pin_hash"));
        u.setUserType(rs.getString("user_type"));
        u.setStatus(rs.getString("status"));
        u.setIsLocked(rs.getString("is_locked"));
        u.setFailedAttempts(rs.getInt("failed_attempts"));
        u.setCreatedAt(rs.getDate("created_at"));

        return u;
    }
}
