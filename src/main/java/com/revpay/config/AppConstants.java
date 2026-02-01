package com.revpay.config;

public class AppConstants {

    // User types
    public static final String USER_PERSONAL = "PERSONAL";
    public static final String USER_BUSINESS = "BUSINESS";

    // Account status
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_LOCKED = "LOCKED";

    // Yes / No flags
    public static final String YES = "Y";
    public static final String NO = "N";

    // Transaction types
    public static final String TXN_ADD_MONEY = "ADD_MONEY";
    public static final String TXN_SEND = "SEND";
    public static final String TXN_REQUEST_PAYMENT = "REQUEST_PAYMENT";

    // Transaction status
    public static final String TXN_SUCCESS = "SUCCESS";
    public static final String TXN_FAILED = "FAILED";
    public static final String TXN_PENDING = "PENDING";

    // Money request status
    public static final String REQUEST_PENDING = "PENDING";
    public static final String REQUEST_ACCEPTED = "ACCEPTED";
    public static final String REQUEST_DECLINED = "DECLINED";

    // Invoice status
    public static final String INVOICE_PENDING = "PENDING";
    public static final String INVOICE_PAID = "PAID";
    public static final String INVOICE_CANCELLED = "CANCELLED";

    // Loan status
    public static final String LOAN_APPLIED = "APPLIED";
    public static final String LOAN_APPROVED = "APPROVED";
    public static final String LOAN_REJECTED = "REJECTED";

    private AppConstants() {
        // prevent instantiation
    }
}
