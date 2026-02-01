package com.revpay.model;

import java.util.Date;

public class Wallet {

    private int walletId;
    private int userId;
    private double balance;
    private double minBalance;
    private Date lastUpdated;

    public Wallet() {}

    public Wallet(int walletId, int userId, double balance,
                  double minBalance, Date lastUpdated) {
        this.walletId = walletId;
        this.userId = userId;
        this.balance = balance;
        this.minBalance = minBalance;
        this.lastUpdated = lastUpdated;
    }

    public int getWalletId() { return walletId; }
    public void setWalletId(int walletId) { this.walletId = walletId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public double getMinBalance() { return minBalance; }
    public void setMinBalance(double minBalance) { this.minBalance = minBalance; }

    public Date getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(Date lastUpdated) { this.lastUpdated = lastUpdated; }
}
