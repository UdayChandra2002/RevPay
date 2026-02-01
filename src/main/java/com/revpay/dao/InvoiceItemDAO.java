package com.revpay.dao;

import com.revpay.config.DBConnection;
import com.revpay.model.InvoiceItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceItemDAO {

    /**
     * Insert a single invoice item
     */
    public void addInvoiceItem(InvoiceItem item) throws SQLException {

        String sql = """
            INSERT INTO invoice_items (
                item_id,
                invoice_id,
                item_name,
                quantity,
                price
            )
            VALUES (
                invoice_seq.NEXTVAL,
                ?, ?, ?, ?
            )
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, item.getInvoiceId());
            ps.setString(2, item.getItemName());
            ps.setInt(3, item.getQuantity());
            ps.setDouble(4, item.getPrice());

            ps.executeUpdate();
        }
    }

    /**
     * Insert multiple items for one invoice (loop call)
     */
    public void addInvoiceItems(List<InvoiceItem> items) throws SQLException {
        for (InvoiceItem item : items) {
            addInvoiceItem(item);
        }
    }

    /**
     * Fetch all items of a given invoice
     */
    public List<InvoiceItem> getItemsByInvoiceId(int invoiceId) throws SQLException {

        List<InvoiceItem> list = new ArrayList<>();

        String sql = """
            SELECT *
            FROM invoice_items
            WHERE invoice_id = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, invoiceId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                InvoiceItem item = new InvoiceItem();
                item.setItemId(rs.getInt("item_id"));
                item.setInvoiceId(rs.getInt("invoice_id"));
                item.setItemName(rs.getString("item_name"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("price"));
                list.add(item);
            }
        }
        return list;
    }

    /**
     * Delete all items when invoice is cancelled/deleted
     */
    public void deleteItemsByInvoiceId(int invoiceId) throws SQLException {

        String sql = "DELETE FROM invoice_items WHERE invoice_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, invoiceId);
            ps.executeUpdate();
        }
    }
}
