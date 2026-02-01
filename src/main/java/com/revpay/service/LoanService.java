package com.revpay.service;

import com.revpay.dao.LoanDAO;
import com.revpay.model.Loan;
import com.revpay.model.Notification;

import java.sql.ResultSet;

public class LoanService {

    private final LoanDAO loanDAO = new LoanDAO();
    private final NotificationService notificationService =
            new NotificationService();

    /* ================= APPLY FOR LOAN ================= */

    public void applyLoan(Loan loan) throws Exception {

        if (loan.getAmount() <= 0) {
            throw new Exception("Loan amount must be greater than zero");
        }

        loanDAO.applyLoan(loan);

        // 🔔 Notification
        Notification n = new Notification();
        n.setUserId(loan.getBusinessId());
        n.setType("LOAN");
        n.setMessage(
                "Loan application submitted for ₹" + loan.getAmount()
        );

        notificationService.sendNotification(n);
    }

    /* ================= VIEW LOAN STATUS ================= */

    public ResultSet getLoansByBusiness(int businessId) throws Exception {
        return loanDAO.getLoansByBusiness(businessId);
    }
}
