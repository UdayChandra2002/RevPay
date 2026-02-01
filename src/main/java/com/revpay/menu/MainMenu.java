package com.revpay.menu;

import java.util.Scanner;

public class MainMenu {

    public void show() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("""
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
