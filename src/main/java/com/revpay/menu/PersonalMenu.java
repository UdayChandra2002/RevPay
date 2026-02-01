package com.revpay.menu;

import com.revpay.model.User;
import com.revpay.service.WalletService;

import java.util.Scanner;

public class PersonalMenu {

    private final User user;

    public PersonalMenu(User user) {
        this.user = user;
    }

    public void show() {
        Scanner sc = new Scanner(System.in);
        WalletService walletService = new WalletService();

        while (true) {
            System.out.println("""
                ===== PERSONAL MENU =====
                1. View Wallet Balance
                2. Add Money
                3. Send Money
                4. Request Money
                5. View Transactions
                6. Add / Manage Payment Methods
                7. View Notifications
                8. Change Password / Transaction PIN
                9. Logout
                """);

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

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
                    case 6 -> new PaymentMethodMenu(user).show();
                    case 7 -> new NotificationMenu(user).show();
                    case 8 -> new SecurityMenu(user).show();
                    case 9 -> {
                        System.out.println("Logged out.");
                        return;
                    }
                    default -> System.out.println("Invalid choice");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
