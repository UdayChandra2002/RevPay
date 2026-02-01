package com.revpay.menu;

import com.revpay.model.Loan;
import com.revpay.model.User;
import com.revpay.service.LoanService;

import java.util.Scanner;

public class LoanMenu {

    private final User user;

    public LoanMenu(User user) {
        this.user = user;
    }

    public void show() {

        // 🔒 Safety check
        if (!"BUSINESS".equalsIgnoreCase(user.getUserType())) {
            System.out.println("Loan facility is available only for business accounts.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        LoanService loanService = new LoanService();

        System.out.println("===== APPLY FOR BUSINESS LOAN =====");

        try {
            System.out.print("Enter Loan Amount: ");
            double amount = sc.nextDouble();
            sc.nextLine();

            if (amount <= 0) {
                System.out.println("Invalid loan amount.");
                return;
            }

            System.out.print("Enter Purpose of Loan: ");
            String purpose = sc.nextLine();

            System.out.print("Enter Tenure (in months): ");
            int tenure = sc.nextInt();

            System.out.print("Enter Interest Rate (%): ");
            double interestRate = sc.nextDouble();

            Loan loan = new Loan();
            loan.setBusinessId(user.getUserId());
            loan.setAmount(amount);
            loan.setPurpose(purpose);
            loan.setTenureMonths(tenure);
            loan.setInterestRate(interestRate);

            loanService.applyLoan(loan);

            System.out.println(" Loan application submitted successfully!");
            System.out.println("Status: APPLIED");

        } catch (Exception e) {
            System.out.println(" Failed to apply for loan: " + e.getMessage());
        }
    }
}
