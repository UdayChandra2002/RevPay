package com.revpay.model;

import java.util.Date;

public class User {

    private int userId;
    private String fullName;
    private String email;
    private String phone;
    private String passwordHash;
    private String transactionPinHash;
    private String userType;
    private String status;
    private int failedAttempts;
    private String isLocked;
    private String securityCode;
    private Date securityCodeExpiry;
    private Date lastLogin;
    private Date createdAt;

    public User() {}

    public User(int userId, String fullName, String email, String phone,
                String passwordHash, String transactionPinHash, String userType,
                String status, int failedAttempts, String isLocked,
                String securityCode, Date securityCodeExpiry,
                Date lastLogin, Date createdAt) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.passwordHash = passwordHash;
        this.transactionPinHash = transactionPinHash;
        this.userType = userType;
        this.status = status;
        this.failedAttempts = failedAttempts;
        this.isLocked = isLocked;
        this.securityCode = securityCode;
        this.securityCodeExpiry = securityCodeExpiry;
        this.lastLogin = lastLogin;
        this.createdAt = createdAt;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getTransactionPinHash() { return transactionPinHash; }
    public void setTransactionPinHash(String transactionPinHash) { this.transactionPinHash = transactionPinHash; }

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getFailedAttempts() { return failedAttempts; }
    public void setFailedAttempts(int failedAttempts) { this.failedAttempts = failedAttempts; }

    public String getIsLocked() { return isLocked; }
    public void setIsLocked(String isLocked) { this.isLocked = isLocked; }

    public String getSecurityCode() { return securityCode; }
    public void setSecurityCode(String securityCode) { this.securityCode = securityCode; }

    public Date getSecurityCodeExpiry() { return securityCodeExpiry; }
    public void setSecurityCodeExpiry(Date securityCodeExpiry) { this.securityCodeExpiry = securityCodeExpiry; }

    public Date getLastLogin() { return lastLogin; }
    public void setLastLogin(Date lastLogin) { this.lastLogin = lastLogin; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
