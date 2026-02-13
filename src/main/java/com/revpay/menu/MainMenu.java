package com.revpay.menu;

import java.util.Scanner;

import com.revpay.main.RevPayApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainMenu {
    private static final Logger logger =
            LogManager.getLogger(MainMenu.class);

    public void show() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            logger.info("""
                
                ===== REV PAY =====
                1. Register
                2. Login
                3. Exit
                """);

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> new RegisterMenu().show();
                case 2 -> new LoginMenu().show();
                case 3 -> {
                    System.out.println("Thank you for using RevPay!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }
}
