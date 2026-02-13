package com.revpay.dao;

import com.revpay.config.DBConnection;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class BusinessAnalyticsDAO {

    /* ================= TOTAL REVENUE ================= */

    public double getTotalRevenue(int businessId) throws SQLException {

        String sql = """
         SELECT SUM(amount)
         FROM transactions
         WHERE receiver_id = ?
           AND status = 'SUCCESS'
         
                                    
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, businessId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble(1);
            }
        }
        return 0;
    }

    /* ================= OUTSTANDING INVOICES ================= */

    public ResultSet getOutstandingInvoices(int businessId) throws SQLException {

        String sql = """
            SELECT invoice_id, customer, amount, due_date
            FROM invoices
            WHERE business_id = ?
              AND status = 'PENDING'
            ORDER BY due_date
        """;

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, businessId);

        return ps.executeQuery(); // closed in service
    }

    /* ================= MONTHLY TRANSACTIONS ================= */

    public Map<String, Integer> getMonthlyTransactions(int businessId)
            throws SQLException {

        Map<String, Integer> map = new LinkedHashMap<>();

        String sql = """
            SELECT TO_CHAR(txn_date,'MON-YYYY') AS month,
                   COUNT(*) AS txn_count
            FROM transactions
            WHERE receiver_id = ?
            GROUP BY TO_CHAR(txn_date,'MON-YYYY')
            ORDER BY MIN(txn_date)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, businessId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                map.put(rs.getString("month"),
                        rs.getInt("txn_count"));
            }
        }
        return map;
    }

    /* ================= TOP CUSTOMERS ================= */

    public ResultSet getTopCustomers(int businessId) throws SQLException {

        String sql = """
        SELECT sender_id, SUM(amount) AS total_paid
        FROM transactions
        WHERE receiver_id = ?
          AND status = 'SUCCESS'
        GROUP BY sender_id
        ORDER BY total_paid DESC
        """;

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, businessId);

        return ps.executeQuery(); // closed in service
    }
}
