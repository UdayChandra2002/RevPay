package com.revpay.menu;

import com.revpay.main.RevPayApplication;
import com.revpay.model.Invoice;
import com.revpay.model.InvoiceItem;
import com.revpay.model.User;
import com.revpay.service.InvoiceService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InvoiceMenu {
    private static final Logger logger =
            LogManager.getLogger(InvoiceMenu.class);
    private final User user;

    public InvoiceMenu(User user) {
        this.user = user;
    }

    public void show() {

        Scanner sc = new Scanner(System.in);
        InvoiceService service = new InvoiceService();

        Invoice invoice = new Invoice();
        invoice.setBusinessId(user.getUserId());
        invoice.setStatus("PENDING");

        logger.info("===== CREATE INVOICE =====");

        /* Customer */
        System.out.print("Enter Customer Name: ");
        invoice.setCustomer(sc.nextLine());

        List<InvoiceItem> items = new ArrayList<>();
        double totalAmount = 0;

        /* Items loop */
        while (true) {
            InvoiceItem item = new InvoiceItem();

            System.out.print("Item Name: ");
            item.setItemName(sc.nextLine());

            System.out.print("Quantity: ");
            int qty = sc.nextInt();

            System.out.print("Price per unit: ");
            double price = sc.nextDouble();
            sc.nextLine();

            item.setQuantity(qty);
            item.setPrice(price);

            totalAmount += qty * price;
            items.add(item);

            System.out.print("Add another item? (Y/N): ");
            if (!sc.nextLine().equalsIgnoreCase("Y")) {
                break;
            }
        }

        /* Show summary */
        System.out.println("\n----- Invoice Summary -----");
        items.forEach(i ->
                System.out.println(
                        i.getItemName() + " | " +
                                i.getQuantity() + " x " +
                                i.getPrice() + " = ₹" +
                                (i.getQuantity() * i.getPrice())
                )
        );

        System.out.println("Total Amount: ₹" + totalAmount);
        invoice.setAmount(totalAmount);

        /* Due date */
        try {
            System.out.print("Enter Due Date (DD-MM-YYYY): ");
            String dateStr = sc.nextLine();

            Date dueDate =
                    new SimpleDateFormat("dd-MM-yyyy").parse(dateStr);

            invoice.setDueDate(dueDate);

            int invoiceId =
                    service.createInvoice(invoice, items);

            logger.info("\nInvoice created successfully!");
            logger.info("Invoice ID: " + invoiceId);
            logger.info("Status: PENDING");

        } catch (Exception e) {
            logger.info("Failed to create invoice: " + e.getMessage());
        }
    }
}
