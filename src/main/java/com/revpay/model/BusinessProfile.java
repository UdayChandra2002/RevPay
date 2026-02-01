package com.revpay.model;

public class BusinessProfile {

    private int businessId;
    private String businessName;
    private String businessType;
    private String taxId;
    private String address;
    private String verified;

    public BusinessProfile() {}

    public BusinessProfile(int businessId, String businessName,
                           String businessType, String taxId,
                           String address, String verified) {
        this.businessId = businessId;
        this.businessName = businessName;
        this.businessType = businessType;
        this.taxId = taxId;
        this.address = address;
        this.verified = verified;
    }

    public int getBusinessId() { return businessId; }
    public void setBusinessId(int businessId) { this.businessId = businessId; }

    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }

    public String getBusinessType() { return businessType; }
    public void setBusinessType(String businessType) { this.businessType = businessType; }

    public String getTaxId() { return taxId; }
    public void setTaxId(String taxId) { this.taxId = taxId; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getVerified() { return verified; }
    public void setVerified(String verified) { this.verified = verified; }
}
