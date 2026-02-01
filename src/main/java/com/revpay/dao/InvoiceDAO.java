package com.revpay.dao;

import com.revpay.config.DBConnection;
import com.revpay.model.Invoice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {

    public int createInvoice(Invoice inv) throws SQLException {
        String sql = """
            INSERT INTO invoices (
                invoice_id, business_id, customer,
                amount, status, due_date
            )
            VALUES (invoice_seq.NEXTVAL, ?, ?, ?, ?, ?)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, new String[]{"invoice_id"})) {

            ps.setInt(1, inv.getBusinessId());
            ps.setString(2, inv.getCustomer());
            ps.setDouble(3, inv.getAmount());
            ps.setString(4, inv.getStatus());
            ps.setDate(5, new java.sql.Date(inv.getDueDate().getTime()));
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    public List<Invoice> getInvoicesByBusiness(int businessId) throws SQLException {
        List<Invoice> list = new ArrayList<>();
        String sql = "SELECT * FROM invoices WHERE business_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, businessId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Invoice i = new Invoice();
                i.setInvoiceId(rs.getInt("invoice_id"));
                i.setCustomer(rs.getString("customer"));
                i.setAmount(rs.getDouble("amount"));
                i.setStatus(rs.getString("status"));
                list.add(i);
            }
        }
        return list;
    }
    public List<Invoice> getInvoicesByStatus(int businessId, String status)
            throws SQLException {

        List<Invoice> list = new ArrayList<>();

        String sql = """
        SELECT *
        FROM invoices
        WHERE business_id = ?
          AND status = ?
        ORDER BY created_at DESC
    """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, businessId);
            ps.setString(2, status);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Invoice i = new Invoice();
                i.setInvoiceId(rs.getInt("invoice_id"));
                i.setCustomer(rs.getString("customer"));
                i.setAmount(rs.getDouble("amount"));
                i.setStatus(rs.getString("status"));
                i.setDueDate(rs.getDate("due_date"));
                list.add(i);
            }
        }
        return list;
    }


    public void cancelInvoice(int invoiceId) throws SQLException {

        String sql = """
        UPDATE invoices
        SET status = 'CANCELLED'
        WHERE invoice_id = ?
          AND status = 'PENDING'
    """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, invoiceId);
            ps.executeUpdate();
        }
    }


}
