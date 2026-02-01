package com.revpay.model;

import java.util.Date;

public class PaymentMethod {

    private int pmId;
    private int userId;
    private String methodType;
    private String detailsEnc;
    private String displayName;
    private String last4;
    private Date expiryDate;
    private String isDefault;

    public PaymentMethod() {}

    public PaymentMethod(int pmId, int userId, String methodType,
                         String detailsEnc, String displayName,
                         String last4, Date expiryDate, String isDefault) {
        this.pmId = pmId;
        this.userId = userId;
        this.methodType = methodType;
        this.detailsEnc = detailsEnc;
        this.displayName = displayName;
        this.last4 = last4;
        this.expiryDate = expiryDate;
        this.isDefault = isDefault;
    }

    public int getPmId() { return pmId; }
    public void setPmId(int pmId) { this.pmId = pmId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getMethodType() { return methodType; }
    public void setMethodType(String methodType) { this.methodType = methodType; }

    public String getDetailsEnc() { return detailsEnc; }
    public void setDetailsEnc(String detailsEnc) { this.detailsEnc = detailsEnc; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getLast4() { return last4; }
    public void setLast4(String last4) { this.last4 = last4; }

    public Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }

    public String getIsDefault() { return isDefault; }
    public void setIsDefault(String isDefault) { this.isDefault = isDefault; }
}
