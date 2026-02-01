package com.revpay.menu;

import com.revpay.model.User;
import com.revpay.service.BusinessAnalyticsService;

import java.util.Scanner;

public class BusinessAnalyticsMenu {

    private final User user;

    public BusinessAnalyticsMenu(User user) {
        this.user = user;
    }

    public void show() {

        Scanner sc = new Scanner(System.in);
        BusinessAnalyticsService service =
                new BusinessAnalyticsService();

        while (true) {
            System.out.println("""
                ===== BUSINESS ANALYTICS =====
                1. Total Revenue
                2. Outstanding Invoices
                3. Monthly Transactions
                4. Top Customers
                5. Back
                """);

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            try {
                switch (choice) {

                    case 1 -> service.showTotalRevenue(user.getUserId());

                    case 2 -> service.showOutstandingInvoices(user.getUserId());

                    case 3 -> service.showMonthlyTransactions(user.getUserId());

                    case 4 -> service.showTopCustomers(user.getUserId());

                    case 5 -> {
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
