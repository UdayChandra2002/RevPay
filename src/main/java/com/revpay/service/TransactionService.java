package com.revpay.service;

import com.revpay.dao.TransactionDAO;
import com.revpay.dao.WalletDAO;
import com.revpay.model.Notification;
import com.revpay.model.Transaction;
import com.revpay.model.Wallet;

import java.util.List;

public class TransactionService {

    private final WalletDAO walletDAO = new WalletDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();
    private final NotificationService notificationService =
            new NotificationService();

    /* ================= SEND MONEY ================= */

    public void sendMoney(int senderId, int receiverId, double amount)
            throws Exception {

        if (senderId == receiverId) {
            throw new Exception("Sender and receiver cannot be the same.");
        }

        if (amount <= 0) {
            throw new Exception("Amount must be greater than zero.");
        }

        Wallet senderWallet = walletDAO.getWalletByUserId(senderId);
        Wallet receiverWallet = walletDAO.getWalletByUserId(receiverId);

        if (senderWallet == null || receiverWallet == null) {
            throw new Exception("Wallet not found.");
        }

        if (senderWallet.getBalance() < amount) {
            throw new Exception("Insufficient balance.");
        }

        /* ✅ SINGLE SOURCE OF WALLET UPDATE */
        walletDAO.updateBalance(
                senderId,
                senderWallet.getBalance() - amount
        );

        walletDAO.updateBalance(
                receiverId,
                receiverWallet.getBalance() + amount
        );

        /* ✅ Record transaction */
        Transaction txn = new Transaction();
        txn.setSenderId(senderId);
        txn.setReceiverId(receiverId);
        txn.setAmount(amount);
        txn.setTxnType("SEND");
        txn.setStatus("SUCCESS");
        txn.setRemarks("Money sent");

        transactionDAO.insertTransaction(txn);

        /* 🔔 Notifications */
        Notification senderN = new Notification();
        senderN.setUserId(senderId);
        senderN.setMessage("You sent ₹" + amount + " to User " + receiverId);
        senderN.setType("TRANSACTION");
        notificationService.sendNotification(senderN);

        Notification receiverN = new Notification();
        receiverN.setUserId(receiverId);
        receiverN.setMessage("You received ₹" + amount + " from User " + senderId);
        receiverN.setType("TRANSACTION");
        notificationService.sendNotification(receiverN);
    }

    /* ================= ADD MONEY ================= */

    public void recordAddMoney(int userId, double amount)
            throws Exception {

        if (amount <= 0) {
            throw new Exception("Amount must be greater than zero.");
        }

        Wallet wallet = walletDAO.getWalletByUserId(userId);

        if (wallet == null) {
            throw new Exception("Wallet not found.");
        }

        /* ✅ Wallet update ONLY here */
        walletDAO.updateBalance(
                userId,
                wallet.getBalance() + amount
        );

        /* ✅ Record transaction */
        Transaction txn = new Transaction();
        txn.setSenderId(userId);     // self
        txn.setReceiverId(userId);   // self
        txn.setAmount(amount);
        txn.setTxnType("ADD_MONEY");
        txn.setStatus("SUCCESS");
        txn.setRemarks("Money added to wallet");

        transactionDAO.insertTransaction(txn);

        /* 🔔 Notification */
        Notification n = new Notification();
        n.setUserId(userId);
        n.setMessage("₹" + amount + " added to your wallet");
        n.setType("WALLET");
        notificationService.sendNotification(n);
    }

    /* ================= VIEW TRANSACTIONS ================= */

    public List<Transaction> getTransactionsByUser(int userId)
            throws Exception {
        return transactionDAO.getTransactionsByUser(userId);
    }
}
