package com.revpay.dao;

import com.revpay.config.DBConnection;
import com.revpay.model.PaymentMethod;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentMethodDAO {

    /* ================= ADD CARD ================= */

    public void addCard(PaymentMethod pm) throws SQLException {

        String sql = """
            INSERT INTO payment_methods (
                pm_id,
                user_id,
                method_type,
                details_enc,
                display_name,
                last4,
                expiry_date,
                is_default
            )
            VALUES (
                pm_seq.NEXTVAL,
                ?, ?, ?, ?, ?, ?, ?
            )
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pm.getUserId());
            ps.setString(2, pm.getMethodType());
            ps.setString(3, pm.getDetailsEnc());
            ps.setString(4, pm.getDisplayName());
            ps.setString(5, pm.getLast4());
            ps.setDate(6, new java.sql.Date(pm.getExpiryDate().getTime()));
            ps.setString(7, pm.getIsDefault());

            ps.executeUpdate();
        }
    }

    /* ================= CLEAR DEFAULT CARD ================= */

    public void clearDefaultForUser(int userId) throws SQLException {

        String sql = """
            UPDATE payment_methods
            SET is_default = 'N'
            WHERE user_id = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }

    /* ================= VIEW CARDS ================= */

    public List<PaymentMethod> getCardsByUser(int userId) throws SQLException {

        List<PaymentMethod> list = new ArrayList<>();

        String sql = """
            SELECT pm_id, display_name, last4, is_default
            FROM payment_methods
            WHERE user_id = ?
            ORDER BY is_default DESC, pm_id
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PaymentMethod pm = new PaymentMethod();
                pm.setPmId(rs.getInt("pm_id"));
                pm.setDisplayName(rs.getString("display_name"));
                pm.setLast4(rs.getString("last4"));
                pm.setIsDefault(rs.getString("is_default"));
                list.add(pm);
            }
        }
        return list;
    }

    /* ================= REMOVE CARD (USER SAFE) ================= */

    public void removeCard(int pmId, int userId) throws SQLException {

        String sql = """
            DELETE FROM payment_methods
            WHERE pm_id = ?
              AND user_id = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pmId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }
}
