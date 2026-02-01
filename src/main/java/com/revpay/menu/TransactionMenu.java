package com.revpay.menu;

import com.revpay.model.Transaction;
import com.revpay.model.User;
import com.revpay.service.TransactionService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TransactionMenu {

    private final User user;

    public TransactionMenu(User user) {
        this.user = user;
    }

    public void show() {

        Scanner sc = new Scanner(System.in);
        TransactionService service = new TransactionService();

        System.out.println("""
            ===== TRANSACTION HISTORY =====
            1. View All Transactions
            2. View Sent Transactions
            3. View Received Transactions
            4. Filter by Date Range
            5. Filter by Amount Range
            6. Filter by Transaction Type
            7. Filter by Status
            8. Back
        """);

        System.out.print("Enter choice: ");
        int choice = sc.nextInt();
        sc.nextLine(); // consume newline

        try {
            List<Transaction> list =
                    service.getTransactionsByUser(user.getUserId());

            if (list.isEmpty()) {
                System.out.println("No transactions found.");
                return;
            }

            switch (choice) {

                /* ================= ALL ================= */
                case 1 -> list.forEach(this::printTxn);

                /* ================= SENT ================= */
                case 2 -> list.stream()
                        .filter(t -> t.getSenderId() == user.getUserId())
                        .forEach(this::printTxn);

                /* ================= RECEIVED ================= */
                case 3 -> list.stream()
                        .filter(t -> t.getReceiverId() == user.getUserId())
                        .forEach(this::printTxn);

                /* ================= DATE RANGE ================= */
                case 4 -> {
                    DateTimeFormatter formatter =
                            DateTimeFormatter.ofPattern("dd-MM-yy");

                    System.out.print("From date (DD-MM-YY): ");
                    LocalDate from =
                            LocalDate.parse(sc.nextLine(), formatter);

                    System.out.print("To date (DD-MM-YY): ");
                    LocalDate to =
                            LocalDate.parse(sc.nextLine(), formatter);

                    list.stream()
                            .filter(t -> t.getTxnDate() != null)
                            .filter(t -> {
                                LocalDate txnDate = t.getTxnDate()
                                        .toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate();

                                return !txnDate.isBefore(from)
                                        && !txnDate.isAfter(to);
                            })
                            .forEach(this::printTxn);
                }

                /* ================= AMOUNT RANGE ================= */
                case 5 -> {
                    System.out.print("Min amount: ");
                    double min = sc.nextDouble();

                    System.out.print("Max amount: ");
                    double max = sc.nextDouble();

                    list.stream()
                            .filter(t -> t.getAmount() >= min && t.getAmount() <= max)
                            .forEach(this::printTxn);
                }

                /* ================= TYPE ================= */
                case 6 -> {
                    System.out.print("Enter transaction type (SEND / ADD_MONEY / REQUEST_PAYMENT): ");
                    String type = sc.nextLine();

                    list.stream()
                            .filter(t -> t.getTxnType() != null)
                            .filter(t -> t.getTxnType().equalsIgnoreCase(type))
                            .forEach(this::printTxn);
                }

                /* ================= STATUS ================= */
                case 7 -> {
                    System.out.print("Enter status (SUCCESS / FAILED / PENDING): ");
                    String status = sc.nextLine();

                    list.stream()
                            .filter(t -> t.getStatus() != null)
                            .filter(t -> t.getStatus().equalsIgnoreCase(status))
                            .forEach(this::printTxn);
                }

                case 8 -> {
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void printTxn(Transaction t) {
        System.out.println(
                "TxnID: " + t.getTxnId() +
                        " | From: " + t.getSenderId() +
                        " | To: " + t.getReceiverId() +
                        " | ₹" + t.getAmount() +
                        " | " + t.getTxnType() +
                        " | " + t.getStatus() +
                        " | " + t.getTxnDate()
        );
    }
}
