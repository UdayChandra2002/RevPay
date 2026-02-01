package com.revpay.menu;

import com.revpay.model.Invoice;
import com.revpay.model.User;
import com.revpay.service.InvoiceService;

import java.util.List;
import java.util.Scanner;

public class ManageInvoiceMenu {

    private final User user;

    public ManageInvoiceMenu(User user) {
        this.user = user;
    }

    public void show() {

        Scanner sc = new Scanner(System.in);
        InvoiceService service = new InvoiceService();

        while (true) {
            System.out.println("""
                ===== MANAGE INVOICES =====
                1. View All Invoices
                2. View Pending Invoices
                3. View Paid Invoices
                4. Cancel Invoice
                5. Back
            """);

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            try {
                switch (choice) {

                    case 1 -> {
                        List<Invoice> list =
                                service.getInvoices(user.getUserId());
                        printInvoices(list);
                    }

                    case 2 -> {
                        List<Invoice> list =
                                service.getInvoicesByStatus(
                                        user.getUserId(), "PENDING");
                        printInvoices(list);
                    }

                    case 3 -> {
                        List<Invoice> list =
                                service.getInvoicesByStatus(
                                        user.getUserId(), "PAID");
                        printInvoices(list);
                    }

                    case 4 -> {
                        System.out.print("Enter Invoice ID to cancel: ");
                        int invoiceId = sc.nextInt();
                        sc.nextLine();

                        service.cancelInvoice(invoiceId);
                        System.out.println("Invoice cancelled successfully.");
                    }

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

    private void printInvoices(List<Invoice> list) {

        if (list.isEmpty()) {
            System.out.println("No invoices found.");
            return;
        }

        System.out.println(
                "Invoice ID | Customer | Amount | Status | Due Date");

        for (Invoice i : list) {
            System.out.println(
                    i.getInvoiceId() + " | " +
                    i.getCustomer() + " | ₹" +
                    i.getAmount() + " | " +
                    i.getStatus() + " | " +
                    i.getDueDate()
            );
        }
    }
}
