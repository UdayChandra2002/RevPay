package com.revpay.menu;

import com.revpay.main.RevPayApplication;
import com.revpay.model.User;
import com.revpay.service.LoanService;

import java.sql.ResultSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoanStatusMenu {
    private static final Logger logger =
            LogManager.getLogger(LoanStatusMenu.class);
    private final User user;

    public LoanStatusMenu(User user) {
        this.user = user;
    }

    public void show() {

        LoanService loanService = new LoanService();

        try {
            ResultSet rs =
                    loanService.getLoansByBusiness(user.getUserId());

            logger.info("===== LOAN STATUS =====");

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
                logger.info("No loan applications found.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
