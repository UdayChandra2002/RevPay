package com.revpay.menu;

import com.revpay.main.RevPayApplication;
import com.revpay.model.PaymentMethod;
import com.revpay.model.User;
import com.revpay.service.PaymentMethodService;
import com.revpay.util.EncryptionUtil;

import java.sql.Date;
import java.util.Calendar;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PaymentMethodMenu {
    private static final Logger logger =
            LogManager.getLogger(PaymentMethodMenu.class);
    private final User user;

    public PaymentMethodMenu(User user) {
        this.user = user;
    }

    public void show() {
        Scanner sc = new Scanner(System.in);
        PaymentMethodService service = new PaymentMethodService();

        while (true) {
            logger.info("""
                ===== PAYMENT METHODS =====
                1. Add Card
                2. View Cards
                3. Remove Card
                4. Back
                """);

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            try {
                switch (choice) {

                    case 1 -> addCard(sc, service);

                    case 2 -> service.viewCards(user.getUserId());

                    case 3 -> {
                        System.out.print("Enter Card ID to remove: ");
                        int pmId = sc.nextInt();
                        sc.nextLine();
                        service.removeCard(pmId, user.getUserId());
                        logger.info("Card removed successfully.");
                    }

                    case 4 -> {
                        return;
                    }

                    default -> System.out.println("Invalid choice.");

                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /* ================= ADD CARD FLOW ================= */

    private void addCard(Scanner sc, PaymentMethodService service) throws Exception {

        PaymentMethod pm = new PaymentMethod();
        pm.setUserId(user.getUserId());
        pm.setMethodType("CARD");

        System.out.print("Display Name: ");
        pm.setDisplayName(sc.nextLine());

        System.out.print("Card Number: ");
        String cardNumber = sc.nextLine();

        /* Extract last 4 digits */
        pm.setLast4(cardNumber.substring(cardNumber.length() - 4));

        /* Encrypt full card number */
        pm.setDetailsEnc(EncryptionUtil.encrypt(cardNumber));

        System.out.print("Expiry Month (MM): ");
        int month = Integer.parseInt(sc.nextLine());

        System.out.print("Expiry Year (YYYY): ");
        int year = Integer.parseInt(sc.nextLine());

        /* Build expiry date = last day of month */
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

        pm.setExpiryDate(new Date(cal.getTimeInMillis()));

        System.out.print("Set as default card? (Y/N): ");
        pm.setIsDefault(sc.nextLine().trim().toUpperCase());

        service.addCard(pm);

        logger.info("Card added successfully.");
    }
}
