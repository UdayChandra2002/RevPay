package com.revpay.menu;

import com.revpay.model.User;
import com.revpay.service.WalletService;

import java.util.Scanner;

public class BusinessMenu {

    private final User user;

    public BusinessMenu(User user) {
        this.user = user;
    }

    public void show() {

        Scanner sc = new Scanner(System.in);
        WalletService walletService = new WalletService();

        while (true) {

            System.out.println("""
                ===== BUSINESS MENU =====
                1. View Wallet Balance
                2. Add Money
                3. Send Money
                4. Request Money
                5. View Transactions
                6. Create Invoice
                7. View / Manage Invoices
                8. Apply for Loan
                9. View Loan Status
                10. View Business Analytics
                11. Add / Manage Payment Methods
                12. View Notifications
                13. Change Password / Transaction PIN
                14. Logout
                """);

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            try {
                switch (choice) {

                    case 1 -> {
                        var wallet = walletService.getWallet(user.getUserId());
                        System.out.println("Wallet Balance: ₹" + wallet.getBalance());
                    }

                    case 2 -> new AddMoneyMenu(user).show();

                    case 3 -> new SendMoneyMenu(user).show();

                    case 4 -> new RequestMoneyMenu(user).show();

                    case 5 -> new TransactionMenu(user).show();

                    case 6 -> new InvoiceMenu(user).show();

                    case 7 -> new ManageInvoiceMenu(user).show();

                    case 8 -> new LoanMenu(user).show();

                    case 9 -> new LoanStatusMenu(user).show();

                    case 10 -> new BusinessAnalyticsMenu(user).show();

                    case 11 -> new PaymentMethodMenu(user).show();

                    case 12 -> new NotificationMenu(user).show();

                    case 13 -> new SecurityMenu(user).show();

                    case 14 -> {
                        System.out.println("Logged out.");
                        return;
                    }

                    default -> System.out.println("Invalid choice.");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
