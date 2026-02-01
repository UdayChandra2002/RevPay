package com.revpay.menu;

import com.revpay.model.User;
import com.revpay.service.LoanService;

import java.sql.ResultSet;

public class LoanStatusMenu {

    private final User user;

    public LoanStatusMenu(User user) {
        this.user = user;
    }

    public void show() {

        LoanService loanService = new LoanService();

        try {
            ResultSet rs =
                    loanService.getLoansByBusiness(user.getUserId());

            System.out.println("===== LOAN STATUS =====");

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                        "Loan ID: " + rs.getInt("loan_id") +
                        " | Amount: ₹" + rs.getDouble("amount") +
                        " | Interest: " + rs.getDouble("interest_rate") + "%" +
                        " | Tenure: " + rs.getInt("tenure_months") + " months" +
                        " | Status: " + rs.getString("status") +
                        " | Applied On: " + rs.getDate("created_at")
                );
            }

            if (!found) {
                System.out.println("No loan applications found.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
