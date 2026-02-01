package com.revpay.menu;

import com.revpay.model.User;
import com.revpay.service.AuthService;

import java.util.Scanner;

public class LoginMenu {

    public void show() {

        Scanner sc = new Scanner(System.in);
        AuthService authService = new AuthService();

        while (true) {

            System.out.print("Email / Phone: ");
            String input = sc.nextLine();

            System.out.print("Password: ");
            String password = sc.nextLine();

            try {
                User user = authService.login(input, password);

                /* ========== LOGIN SUCCESS ========== */
                if ("PERSONAL".equals(user.getUserType())) {
                    new PersonalMenu(user).show();
                } else {
                    new BusinessMenu(user).show();
                }
                return; // exit login menu after success

            } catch (Exception e) {

                System.out.println(e.getMessage());

                System.out.println("""
                    1. Try Again
                    2. Forgot Password
                    3. Exit
                """);

                System.out.print("Enter choice: ");
                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1 -> {
                        // retry loop continues
                    }
                    case 2 -> {
                        try {
                            authService.forgotPasswordFlow(input);
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                        return;
                    }
                    case 3 -> {
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            }
        }
    }
}
