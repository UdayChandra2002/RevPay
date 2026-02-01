package com.revpay.model;

public class InvoiceItem {

    private int itemId;
    private int invoiceId;
    private String itemName;
    private int quantity;
    private double price;

    public InvoiceItem() {}

    public InvoiceItem(int itemId, int invoiceId,
                       String itemName, int quantity, double price) {
        this.itemId = itemId;
        this.invoiceId = invoiceId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public int getInvoiceId() { return invoiceId; }
    public void setInvoiceId(int invoiceId) { this.invoiceId = invoiceId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
