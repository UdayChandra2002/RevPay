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

    /* ================= SEND MONEY ================= */

    public void sendMoney(int senderId, int receiverId, double amount) throws Exception {

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

        /* Update balances */
        walletDAO.updateBalance(senderId, senderWallet.getBalance() - amount);
        walletDAO.updateBalance(receiverId, receiverWallet.getBalance() + amount);

        /* Record transaction */
        Transaction txn = new Transaction();
        txn.setSenderId(senderId);
        txn.setReceiverId(receiverId);
        txn.setAmount(amount);
        txn.setTxnType("SEND");
        txn.setStatus("SUCCESS");
        txn.setRemarks("Money sent");

        transactionDAO.insertTransaction(txn);


        NotificationService notificationService = new NotificationService();

// Sender notification
        Notification senderN = new Notification();
        senderN.setUserId(senderId);
        senderN.setMessage("You sent ₹" + amount + " to User " + receiverId);
        senderN.setType("TRANSACTION");
        notificationService.sendNotification(senderN);

// Receiver notification
        Notification receiverN = new Notification();
        receiverN.setUserId(receiverId);
        receiverN.setMessage("You received ₹" + amount + " from User " + senderId);
        receiverN.setType("TRANSACTION");
        notificationService.sendNotification(receiverN);


    }

    /* ================= ADD MONEY ================= */

    public void recordAddMoney(int userId, double amount) throws Exception {

        if (amount <= 0) {
            throw new Exception("Amount must be greater than zero.");
        }

        Transaction txn = new Transaction();
        txn.setSenderId(userId);
        txn.setReceiverId(userId);          // Wallet owner
        txn.setAmount(amount);
        txn.setTxnType("ADD_MONEY");
        txn.setStatus("SUCCESS");
        txn.setRemarks("Money added to wallet");

        transactionDAO.insertTransaction(txn);
    }


    /* ================= VIEW TRANSACTIONS ================= */

    public List<Transaction> getTransactionsByUser(int userId) throws Exception {
        return transactionDAO.getTransactionsByUser(userId);
    }
}
