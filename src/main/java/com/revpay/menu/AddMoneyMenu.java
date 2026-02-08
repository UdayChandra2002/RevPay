package com.revpay.menu;

import com.revpay.main.RevPayApplication;
import com.revpay.model.PaymentMethod;
import com.revpay.model.User;
import com.revpay.service.PaymentMethodService;
import com.revpay.service.TransactionService;
import com.revpay.service.WalletService;
import com.revpay.util.OTPUtil;
import com.revpay.util.PasswordUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Scanner;

public class AddMoneyMenu {
    private static final Logger logger =
            LogManager.getLogger(AddMoneyMenu.class);
    private final User user;

    public AddMoneyMenu(User user) {
        this.user = user;
    }

    public void show() {

        Scanner sc = new Scanner(System.in);
        PaymentMethodService pmService = new PaymentMethodService();
        WalletService walletService = new WalletService();
        TransactionService txnService = new TransactionService();

        try {
            /*  Show cards */
            List<PaymentMethod> cards = pmService.getCards(user.getUserId());

            if (cards.isEmpty()) {
                System.out.println("No cards found. Please add a card first.");
                return;
            }

            logger.info("===== ADD MONEY =====");
            System.out.println("Select Payment Card:");

            for (int i = 0; i < cards.size(); i++) {
                PaymentMethod pm = cards.get(i);
                System.out.println(
                        (i + 1) + ". " +
                                pm.getDisplayName() +
                                " (**** " + pm.getLast4() + ")"
                );
            }

            System.out.print("Enter choice: ");
            int cardChoice = sc.nextInt();
            sc.nextLine();

            if (cardChoice < 1 || cardChoice > cards.size()) {
                System.out.println("Invalid card selection.");
                return;
            }

            /*  Amount */
            System.out.print("Enter amount to add: ");
            double amount = sc.nextDouble();
            sc.nextLine();

            if (amount <= 0) {
                System.out.println("Invalid amount.");
                return;
            }

            /*  Transaction PIN */
            System.out.print("Enter Transaction PIN: ");
            String enteredPin = sc.nextLine();

            if (!PasswordUtil.verify(enteredPin, user.getTransactionPinHash())) {
                System.out.println("Invalid transaction PIN.");
                return;
            }

            /*  2FA */
            String otp = OTPUtil.generateOTP();
            System.out.println("Your security code is: " + otp);

            System.out.print("Enter security code: ");
            String enteredOtp = sc.nextLine();

            if (!otp.equals(enteredOtp)) {
                System.out.println("Invalid security code.");
                return;
            }

            /*  Add money */
            walletService.addMoney(user.getUserId(), amount);

            /*  Record transaction */
            txnService.recordAddMoney(user.getUserId(), amount);

            logger.info("Money added successfully!");
            logger.info("Updated Wallet Balance: ₹" +
                    walletService.getBalance(user.getUserId()));

        } catch (Exception e) {
            logger.info("Failed to add money: " + e.getMessage());
        }
    }
}
