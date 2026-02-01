package com.revpay.menu;

import com.revpay.model.User;
import com.revpay.service.UserService;

import java.util.Scanner;

public class SecurityMenu {

    private final User user;

    public SecurityMenu(User user) {
        this.user = user;
    }

    public void show() {

        Scanner sc = new Scanner(System.in);
        UserService service = new UserService();

        System.out.println("""
            ===== SECURITY =====
            1. Change Login Password
            2. Change Transaction PIN
            3. Back
        """);

        int choice = sc.nextInt();
        sc.nextLine();

        try {
            switch (choice) {

                case 1 -> {
                    System.out.print("Current Password: ");
                    String cur = sc.nextLine();

                    System.out.print("New Password: ");
                    String newPwd = sc.nextLine();

                    System.out.print("Confirm New Password: ");
                    String confirm = sc.nextLine();

                    service.changePassword(
                            user.getUserId(), cur, newPwd, confirm);

                    System.out.println("Password changed successfully!");
                }

                case 2 -> {
                    System.out.print("Current Transaction PIN: ");
                    String curPin = sc.nextLine();

                    System.out.print("New Transaction PIN: ");
                    String newPin = sc.nextLine();

                    System.out.print("Confirm New PIN: ");
                    String confirm = sc.nextLine();

                    service.changeTransactionPin(
                            user.getUserId(), curPin, newPin, confirm);

                    System.out.println("Transaction PIN updated!");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
