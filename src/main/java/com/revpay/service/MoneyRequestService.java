package com.revpay.service;

import com.revpay.dao.MoneyRequestDAO;
import com.revpay.dao.WalletDAO;
import com.revpay.model.MoneyRequest;
import com.revpay.model.Notification;
import com.revpay.model.Wallet;

import java.util.List;

public class MoneyRequestService {

    private final MoneyRequestDAO requestDAO = new MoneyRequestDAO();
    private final WalletDAO walletDAO = new WalletDAO();
    private final TransactionService transactionService = new TransactionService();
    private final NotificationService notificationService = new NotificationService();

    /* ================= CREATE REQUEST ================= */

    public void createRequest(int senderId, int receiverId, double amount) throws Exception {

        if (senderId == receiverId) {
            throw new Exception("Cannot request money from yourself.");
        }

        if (amount <= 0) {
            throw new Exception("Amount must be greater than zero.");
        }

        requestDAO.createRequest(senderId, receiverId, amount);

        //------------------n
        Notification n = new Notification();
        n.setUserId(receiverId);
        n.setMessage("You have a money request of ₹" + amount + " from User " + senderId);
        n.setType("REQUEST");

        notificationService.sendNotification(n);
        //-------------------n




    }

    /* ================= GET INCOMING REQUESTS ================= */

    public List<MoneyRequest> getIncomingRequests(int userId) throws Exception {
        return requestDAO.getIncomingRequests(userId);
    }

    /* ================= GET SENT REQUESTS ================= */

    public List<MoneyRequest> getSentRequests(int userId) throws Exception {
        return requestDAO.getSentRequests(userId);
    }

    /* ================= ACCEPT REQUEST ================= */

    public void acceptRequest(int requestId) throws Exception {

        MoneyRequest request = requestDAO.getById(requestId);

        if (request == null) {
            throw new Exception("Money request not found.");
        }

        int payerId = request.getReceiverId(); // person who pays
        int payeeId = request.getSenderId();   // person who requested
        double amount = request.getAmount();
        Wallet payerWallet = walletDAO.getWalletByUserId(payerId);


        if (payerWallet == null) {
            throw new Exception("Wallet not found.");
        }

        if (payerWallet.getBalance() < request.getAmount()) {
            throw new Exception("Insufficient balance.");
        }

        /* Update wallets */
        walletDAO.updateBalance(
                payerId,
                payerWallet.getBalance() - request.getAmount()
        );

        Wallet payeeWallet = walletDAO.getWalletByUserId(payeeId);
        walletDAO.updateBalance(
                payeeId,
                payeeWallet.getBalance() + request.getAmount()
        );

        /* Update request status */
        requestDAO.updateStatus(requestId, "ACCEPTED", null);

        /* Record transaction */
        transactionService.sendMoney(
                payerId,
                payeeId,
                request.getAmount()
        );

        //---------------n
        NotificationService notificationService = new NotificationService();

// payer
        Notification n1 = new Notification();
        n1.setUserId(payerId);
        n1.setMessage("You paid ₹" + amount + " for a money request");
        n1.setType("REQUEST");
        notificationService.sendNotification(n1);

// requester
        Notification n2 = new Notification();
        n2.setUserId(payeeId);
        n2.setMessage("Your money request of ₹" + amount + " was accepted");
        n2.setType("REQUEST");
        notificationService.sendNotification(n2);

        //--------------n

    }

    /* ================= DECLINE REQUEST ================= */

    public void declineRequest(int requestId, String reason) throws Exception {

        MoneyRequest request = requestDAO.getById(requestId);

        if (request == null) {
            throw new Exception("Money request not found.");
        }

        requestDAO.updateStatus(requestId, "DECLINED", reason);

        //----------------n
        Notification n = new Notification();
        n.setUserId(request.getSenderId());
        n.setMessage("Your money request was declined");
        n.setType("REQUEST");

        notificationService.sendNotification(n);
        //------------------n
    }
}
