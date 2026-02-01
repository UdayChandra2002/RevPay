package com.revpay.dao;

import com.revpay.config.DBConnection;
import com.revpay.model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    /* ================= INSERT TRANSACTION ================= */

    public void insertTransaction(Transaction txn) throws SQLException {

        String sql = """
INSERT INTO transactions (
    txn_id, sender_id, receiver_id,
    amount, txn_type, status,
    reference_no, remarks, failure_reason,
    txn_date
)
VALUES (txn_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)
""";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            /* sender_id can be NULL (ADD_MONEY) */
            if (txn.getSenderId() == null) {
                ps.setNull(1, Types.INTEGER);
            } else {
                ps.setInt(1, txn.getSenderId());
            }

            /* receiver_id is always present */
            if (txn.getReceiverId() == null) {
                ps.setNull(2, Types.INTEGER);
            } else {
                ps.setInt(2, txn.getReceiverId());
            }

            ps.setDouble(3, txn.getAmount());
            ps.setString(4, txn.getTxnType());
            ps.setString(5, txn.getStatus());
            ps.setString(6, txn.getReferenceNo());
            ps.setString(7, txn.getRemarks());
            ps.setString(8, txn.getFailureReason());

            ps.executeUpdate();
        }
    }

    /* ================= FETCH TRANSACTIONS ================= */

    public List<Transaction> getTransactionsByUser(int userId) throws SQLException {

        List<Transaction> list = new ArrayList<>();

        String sql = """
            SELECT *
            FROM transactions
            WHERE sender_id = ? OR receiver_id = ?
            ORDER BY txn_date DESC
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transaction t = new Transaction();

                t.setTxnId(rs.getInt("txn_id"));

                /* Handle nullable sender_id */
                int sender = rs.getInt("sender_id");
                if (rs.wasNull()) {
                    t.setSenderId(null);
                } else {
                    t.setSenderId(sender);
                }

                /* Handle nullable receiver_id (future-safe) */
                int receiver = rs.getInt("receiver_id");
                if (rs.wasNull()) {
                    t.setReceiverId(null);
                } else {
                    t.setReceiverId(receiver);
                }

                t.setAmount(rs.getDouble("amount"));
                t.setTxnType(rs.getString("txn_type"));
                t.setStatus(rs.getString("status"));
                t.setReferenceNo(rs.getString("reference_no"));
                t.setRemarks(rs.getString("remarks"));
                t.setFailureReason(rs.getString("failure_reason"));
                java.sql.Timestamp ts = rs.getTimestamp("txn_date");
                if (ts != null) {
                    t.setTxnDate(new java.util.Date(ts.getTime()));
                }

                list.add(t);
            }
        }
        return list;
    }
}
