package com.revpay.model;

import java.util.Date;

public class Invoice {

    private int invoiceId;
    private int businessId;
    private String customer;
    private double amount;
    private String status;
    private Date dueDate;
    private Date createdAt;

    public Invoice() {}

    public Invoice(int invoiceId, int businessId, String customer,
                   double amount, String status,
                   Date dueDate, Date createdAt) {
        this.invoiceId = invoiceId;
        this.businessId = businessId;
        this.customer = customer;
        this.amount = amount;
        this.status = status;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
    }

    public int getInvoiceId() { return invoiceId; }
    public void setInvoiceId(int invoiceId) { this.invoiceId = invoiceId; }

    public int getBusinessId() { return businessId; }
    public void setBusinessId(int businessId) { this.businessId = businessId; }

    public String getCustomer() { return customer; }
    public void setCustomer(String customer) { this.customer = customer; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
