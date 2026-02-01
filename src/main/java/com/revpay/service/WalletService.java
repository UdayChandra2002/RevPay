package com.revpay.service;

import com.revpay.dao.WalletDAO;
import com.revpay.model.Notification;
import com.revpay.model.Wallet;

public class WalletService {

    private final WalletDAO walletDAO = new WalletDAO();

    /* ================= GET WALLET ================= */

    public Wallet getWallet(int userId) throws Exception {
        return walletDAO.getWalletByUserId(userId);
    }

    /* ================= ADD MONEY ================= */

    public void addMoney(int userId, double amount) throws Exception {

        if (amount <= 0) {
            throw new Exception("Amount must be greater than zero.");
        }

        Wallet wallet = walletDAO.getWalletByUserId(userId);

        if (wallet == null) {
            throw new Exception("Wallet not found.");
        }

        double newBalance = wallet.getBalance() + amount;
        walletDAO.updateBalance(userId, newBalance);


        //----------------n
        NotificationService notificationService = new NotificationService();
        Notification n = new Notification();
        n.setUserId(userId);
        n.setMessage("₹" + amount + " added to your wallet");
        n.setType("WALLET");

        notificationService.sendNotification(n);
        //---------------n

    }

    /* ================= GET BALANCE ================= */

    public double getBalance(int userId) throws Exception {
        Wallet wallet = walletDAO.getWalletByUserId(userId);
        return wallet != null ? wallet.getBalance() : 0.0;
    }
}
