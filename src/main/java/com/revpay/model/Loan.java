package com.revpay.model;

import java.util.Date;

public class Loan {

    private int loanId;
    private int businessId;
    private double amount;
    private double interestRate;
    private int tenureMonths;
    private String purpose;
    private String status;
    private Date createdAt;

    public Loan() {}

    public Loan(int loanId, int businessId, double amount,
                double interestRate, int tenureMonths,
                String purpose, String status, Date createdAt) {
        this.loanId = loanId;
        this.businessId = businessId;
        this.amount = amount;
        this.interestRate = interestRate;
        this.tenureMonths = tenureMonths;
        this.purpose = purpose;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getLoanId() { return loanId; }
    public void setLoanId(int loanId) { this.loanId = loanId; }

    public int getBusinessId() { return businessId; }
    public void setBusinessId(int businessId) { this.businessId = businessId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public double getInterestRate() { return interestRate; }
    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }

    public int getTenureMonths() { return tenureMonths; }
    public void setTenureMonths(int tenureMonths) { this.tenureMonths = tenureMonths; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
