package com.revpay.menu;

import com.revpay.model.User;
import com.revpay.service.TransactionService;
import com.revpay.service.UserService;
import com.revpay.util.OTPUtil;
import com.revpay.util.PasswordUtil;

import java.util.Scanner;

public class SendMoneyMenu {

    private final User user;

    public SendMoneyMenu(User user) {
        this.user = user;
    }

    public void show() {

        Scanner sc = new Scanner(System.in);
        UserService userService = new UserService();
        TransactionService transactionService = new TransactionService();

        try {
            System.out.println("===== SEND MONEY =====");
            System.out.println("Select receiver lookup:");
            System.out.println("1. User ID");
            System.out.println("2. Email");
            System.out.println("3. Phone");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            User receiver = null;

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter User ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    receiver = userService.getUserById(id);
                }
                case 2 -> {
                    System.out.print("Enter Email: ");
                    receiver = userService.getUserByEmailOrPhone(sc.nextLine());
                }
                case 3 -> {
                    System.out.print("Enter Phone: ");
                    receiver = userService.getUserByEmailOrPhone(sc.nextLine());
                }
                default -> {
                    System.out.println("Invalid choice.");
                    return;
                }
            }

            if (receiver == null) {
                System.out.println("Receiver not found.");
                return;
            }

            if (receiver.getUserId() == user.getUserId()) {
                System.out.println("You cannot send money to yourself.");
                return;
            }

            System.out.print("Enter amount to send: ");
            double amount = sc.nextDouble();
            sc.nextLine();

            if (amount <= 0) {
                System.out.println("Invalid amount.");
                return;
            }

            /* Transaction PIN */
            System.out.print("Enter Transaction PIN: ");
            String pin = sc.nextLine();

            if (!PasswordUtil.verify(pin, user.getTransactionPinHash())) {
                System.out.println("Invalid transaction PIN.");
                return;
            }

            /* 2FA */
            String otp = OTPUtil.generateOTP();
            System.out.println("Your security code is: " + otp);

            System.out.print("Enter security code: ");
            String enteredOtp = sc.nextLine();

            if (!otp.equals(enteredOtp)) {
                System.out.println("Invalid security code.");
                return;
            }

            /* Send money */
            transactionService.sendMoney(
                    user.getUserId(),
                    receiver.getUserId(),
                    amount
            );

            System.out.println("Money sent successfully!");

        } catch (Exception e) {
            System.out.println("Failed to send money: " + e.getMessage());
        }
    }
}
