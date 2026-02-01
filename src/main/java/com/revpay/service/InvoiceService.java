package com.revpay.service;

import com.revpay.dao.InvoiceDAO;
import com.revpay.dao.InvoiceItemDAO;
import com.revpay.model.Invoice;
import com.revpay.model.InvoiceItem;
import com.revpay.model.Notification;

import java.util.List;

public class InvoiceService {

    private final InvoiceDAO invoiceDAO = new InvoiceDAO();
    private final InvoiceItemDAO itemDAO = new InvoiceItemDAO();
    private final NotificationService notificationService =
            new NotificationService();

    /* ================= CREATE INVOICE (OPTION 6) ================= */

    public int createInvoice(Invoice invoice,
                             List<InvoiceItem> items) throws Exception {

        int invoiceId = invoiceDAO.createInvoice(invoice);

        for (InvoiceItem item : items) {
            item.setInvoiceId(invoiceId);
            itemDAO.addInvoiceItem(item);
        }

        // 🔔 Notification
        Notification n = new Notification();
        n.setUserId(invoice.getBusinessId());
        n.setMessage(
                "Invoice #" + invoiceId +
                        " created for ₹" + invoice.getAmount()
        );
        n.setType("INVOICE");

        notificationService.sendNotification(n);

        return invoiceId;
    }

    /* ================= VIEW INVOICES (OPTION 7) ================= */

    public List<Invoice> getInvoices(int businessId) throws Exception {
        return invoiceDAO.getInvoicesByBusiness(businessId);
    }

    public List<Invoice> getInvoicesByStatus(int businessId, String status)
            throws Exception {
        return invoiceDAO.getInvoicesByStatus(businessId, status);
    }

    /* ================= CANCEL INVOICE ================= */

    public void cancelInvoice(int invoiceId) throws Exception {

        invoiceDAO.cancelInvoice(invoiceId);

        // 🔔 Optional notification (business user)
        Notification n = new Notification();
        n.setUserId(invoiceId); // business can see cancel confirmation
        n.setMessage("Invoice " + invoiceId + " has been cancelled");
        n.setType("INVOICE");

        notificationService.sendNotification(n);
    }
}
