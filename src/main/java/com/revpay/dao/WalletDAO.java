package com.revpay.dao;

import com.revpay.config.DBConnection;
import com.revpay.model.Wallet;

import java.sql.*;

public class WalletDAO {

    public void createWallet(int userId) throws SQLException {
        String sql = """
            INSERT INTO wallets (wallet_id, user_id, balance)
            VALUES (wallets_seq.NEXTVAL, ?, 0)
        """;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }

    public Wallet getWalletByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM wallets WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Wallet w = new Wallet();
                w.setWalletId(rs.getInt("wallet_id"));
                w.setUserId(userId);
                w.setBalance(rs.getDouble("balance"));
                w.setMinBalance(rs.getDouble("min_balance"));
                return w;
            }
        }
        return null;
    }

    public void updateBalance(int userId, double newBalance) throws SQLException {
        String sql = "UPDATE wallets SET balance = ? WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, newBalance);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }
}
