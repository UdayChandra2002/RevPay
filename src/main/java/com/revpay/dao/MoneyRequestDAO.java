package com.revpay.dao;

import com.revpay.config.DBConnection;
import com.revpay.model.MoneyRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MoneyRequestDAO {

    /* ================= CREATE REQUEST ================= */

    public void createRequest(int senderId, int receiverId, double amount)
            throws SQLException {

        String sql = """
                INSERT INTO money_requests (
                    request_id, sender_id, receiver_id,
                    amount, status, expiry_date, created_at
                )
                VALUES (
                    requests_seq.NEXTVAL,
                    ?, ?, ?, 'PENDING',
                    SYSDATE + 1,
                    SYSDATE
                )
            """;


        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, senderId);
            ps.setInt(2, receiverId);
            ps.setDouble(3, amount);
            ps.executeUpdate();
        }
    }

    /* ================= GET INCOMING REQUESTS ================= */

    public List<MoneyRequest> getIncomingRequests(int userId)
            throws SQLException {

        List<MoneyRequest> list = new ArrayList<>();

        String sql = """
            SELECT *
            FROM money_requests
            WHERE receiver_id = ?
              AND status = 'PENDING'
            ORDER BY created_at DESC
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MoneyRequest r = mapRequest(rs);
                list.add(r);
            }
        }
        return list;
    }

    /* ================= GET SENT REQUESTS ================= */

    public List<MoneyRequest> getSentRequests(int userId)
            throws SQLException {

        List<MoneyRequest> list = new ArrayList<>();

        String sql = """
            SELECT *
            FROM money_requests
            WHERE sender_id = ?
            ORDER BY created_at DESC
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MoneyRequest r = mapRequest(rs);
                list.add(r);
            }
        }
        return list;
    }

    /* ================= GET REQUEST BY ID ================= */

    public MoneyRequest getById(int requestId)
            throws SQLException {

        String sql = "SELECT * FROM money_requests WHERE request_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, requestId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRequest(rs);
            }
        }
        return null;
    }

    /* ================= UPDATE STATUS ================= */

    public void updateStatus(int requestId, String status, String reason)
            throws SQLException {

        String sql = """
            UPDATE money_requests
            SET status = ?, rejection_reason = ?
            WHERE request_id = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setString(2, reason);
            ps.setInt(3, requestId);
            ps.executeUpdate();
        }
    }

    /* ================= COMMON MAPPER ================= */

    private MoneyRequest mapRequest(ResultSet rs) throws SQLException {

        MoneyRequest r = new MoneyRequest();
        r.setRequestId(rs.getInt("request_id"));
        r.setSenderId(rs.getInt("sender_id"));
        r.setReceiverId(rs.getInt("receiver_id"));
        r.setAmount(rs.getDouble("amount"));
        r.setStatus(rs.getString("status"));
        r.setRejectionReason(rs.getString("rejection_reason"));
        r.setCreatedAt(rs.getDate("created_at"));
        return r;
    }
}
