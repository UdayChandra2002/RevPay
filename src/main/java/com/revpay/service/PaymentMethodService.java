package com.revpay.service;

import com.revpay.dao.PaymentMethodDAO;
import com.revpay.model.PaymentMethod;

import java.util.List;

public class PaymentMethodService {

    private final PaymentMethodDAO paymentMethodDAO = new PaymentMethodDAO();

    /* ================= ADD CARD ================= */

    public void addCard(PaymentMethod pm) throws Exception {

        // If user sets this card as default,
        // remove default flag from other cards
        if ("Y".equalsIgnoreCase(pm.getIsDefault())) {
            paymentMethodDAO.clearDefaultForUser(pm.getUserId());
        }

        paymentMethodDAO.addCard(pm);
    }

    /* ================= VIEW CARDS ================= */

    public void viewCards(int userId) throws Exception {
        List<PaymentMethod> cards = paymentMethodDAO.getCardsByUser(userId);

        if (cards.isEmpty()) {
            System.out.println("No saved cards.");
            return;
        }

        System.out.println("Saved Cards:");
        for (PaymentMethod pm : cards) {
            System.out.println(
                    pm.getPmId() + ". " +
                            pm.getDisplayName() +
                            " (**** " + pm.getLast4() + ")" +
                            ("Y".equals(pm.getIsDefault()) ? " [DEFAULT]" : "")
            );
        }
    }

    /* ================= REMOVE CARD ================= */

    public void removeCard(int pmId, int userId) throws Exception {
        paymentMethodDAO.removeCard(pmId, userId);
    }

    /* ================= FETCH (OPTIONAL) ================= */

    public List<PaymentMethod> getCards(int userId) throws Exception {
        return paymentMethodDAO.getCardsByUser(userId);
    }
}
