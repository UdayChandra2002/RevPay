package com.revpay.model;

import java.util.Date;

public class Transaction {

    private int txnId;
    private Integer senderId;
    private Integer receiverId;
    private double amount;
    private String txnType;
    private String status;
    private String referenceNo;
    private String remarks;
    private String failureReason;
    private Date txnDate;

    public Transaction() {}

    public Transaction(int txnId, int senderId, int receiverId,
                       double amount, String txnType, String status,
                       String referenceNo, String remarks,
                       String failureReason, Date txnDate) {
        this.txnId = txnId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.txnType = txnType;
        this.status = status;
        this.referenceNo = referenceNo;
        this.remarks = remarks;
        this.failureReason = failureReason;
        this.txnDate = txnDate;
    }

    public int getTxnId() { return txnId; }
    public void setTxnId(int txnId) { this.txnId = txnId; }

    public Integer getSenderId() {return senderId;}
    public void setSenderId(Integer senderId) {this.senderId = senderId;}

    public Integer getReceiverId() {return receiverId;}
    public void setReceiverId(Integer receiverId) { this.receiverId = receiverId; }


    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getTxnType() { return txnType; }
    public void setTxnType(String txnType) { this.txnType = txnType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getReferenceNo() { return referenceNo; }
    public void setReferenceNo(String referenceNo) { this.referenceNo = referenceNo; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getFailureReason() { return failureReason; }
    public void setFailureReason(String failureReason) { this.failureReason = failureReason; }

    public Date getTxnDate() { return txnDate; }
    public void setTxnDate(Date txnDate) { this.txnDate = txnDate; }
}
