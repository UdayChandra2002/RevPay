package com.revpay.service;

import com.revpay.dao.BusinessAnalyticsDAO;

import java.sql.ResultSet;
import java.util.Map;

public class BusinessAnalyticsService {

    private final BusinessAnalyticsDAO dao =
            new BusinessAnalyticsDAO();

    /* ================= TOTAL REVENUE ================= */

    public void showTotalRevenue(int businessId) throws Exception {

        double revenue = dao.getTotalRevenue(businessId);
        System.out.println("Total Revenue: ₹" + revenue);
    }

    /* ================= OUTSTANDING INVOICES ================= */

    public void showOutstandingInvoices(int businessId) throws Exception {

        ResultSet rs = dao.getOutstandingInvoices(businessId);

        System.out.println("\nOutstanding Invoices:");
        boolean found = false;

        while (rs.next()) {
            found = true;
            System.out.println(
                    "Invoice " + rs.getInt("invoice_id") +
                            " | Customer: " + rs.getString("customer") +
                            " | ₹" + rs.getDouble("amount") +
                            " | Due: " + rs.getDate("due_date")
            );
        }

        if (!found) {
            System.out.println("No pending invoices.");
        }

        rs.getStatement().getConnection().close();
    }

    /* ================= MONTHLY TRANSACTIONS ================= */

    public void showMonthlyTransactions(int businessId) throws Exception {

        Map<String, Integer> map =
                dao.getMonthlyTransactions(businessId);

        if (map.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        map.forEach((month, count) ->
                System.out.println(month + " : " + count + " transactions")
        );
    }

    /* ================= TOP CUSTOMERS ================= */

    public void showTopCustomers(int businessId) throws Exception {

        ResultSet rs = dao.getTopCustomers(businessId);

        System.out.println("\nTop Customers:");
        boolean found = false;

        while (rs.next()) {
            found = true;
            System.out.println(
                    "User " + rs.getInt("sender_id") +
                            " : ₹" + rs.getDouble("total_paid")
            );
        }

        if (!found) {
            System.out.println("No customer data available.");
        }

        rs.getStatement().getConnection().close();
    }
}
