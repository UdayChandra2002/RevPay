package com.revpay.service;

import com.revpay.model.MoneyRequest;
import com.revpay.model.SecurityQuestion;
import com.revpay.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoneyRequestServiceTest {

    private AuthService authService;
    private WalletService walletService;
    private MoneyRequestService requestService;

    private int requesterId; // asks money
    private int payerId;     // pays money

    @BeforeEach
    void setup() throws Exception {

        authService = new AuthService();
        walletService = new WalletService();
        requestService = new MoneyRequestService();

        // ---------- REQUESTER ----------
        User requester = new User();
        requester.setFullName("Requester");
        requester.setEmail("req_" + System.currentTimeMillis() + "@mail.com");
        requester.setPhone("9" + (int)(Math.random() * 1000000000));
        requester.setUserType("PERSONAL");

        SecurityQuestion sq1 = new SecurityQuestion();
        sq1.setQuestion("Q1?");
        sq1.setAnswerHash("A1");

        requesterId = authService.registerUser(
                requester,
                "password123",
                "1234",
                sq1,
                null
        );

        // ---------- PAYER ----------
        User payer = new User();
        payer.setFullName("Payer");
        payer.setEmail("pay_" + System.currentTimeMillis() + "@mail.com");
        payer.setPhone("8" + (int)(Math.random() * 1000000000));
        payer.setUserType("PERSONAL");

        SecurityQuestion sq2 = new SecurityQuestion();
        sq2.setQuestion("Q2?");
        sq2.setAnswerHash("A2");

        payerId = authService.registerUser(
                payer,
                "password123",
                "1234",
                sq2,
                null
        );

        // Add money to payer wallet
        walletService.addMoney(payerId, 5000);
    }

    /* ================= TEST 1 ================= */
    @Test
    void testCreateMoneyRequest() throws Exception {

        requestService.createRequest(
                requesterId,
                payerId,
                1000
        );

        List<MoneyRequest> incoming =
                requestService.getIncomingRequests(payerId);

        assertEquals(1, incoming.size());
        assertEquals(1000, incoming.get(0).getAmount());
        assertEquals("PENDING", incoming.get(0).getStatus());
    }

    /* ================= TEST 2 ================= */
    @Test
    void testAcceptMoneyRequestAndBalanceUpdate() throws Exception {

        requestService.createRequest(
                requesterId,
                payerId,
                2000
        );

        MoneyRequest request =
                requestService.getIncomingRequests(payerId).get(0);

        requestService.acceptRequest(request.getRequestId());

        double payerBalance =
                walletService.getWallet(payerId).getBalance();

        double requesterBalance =
                walletService.getWallet(requesterId).getBalance();

        assertEquals(3000, payerBalance);      // 5000 - 2000
        assertEquals(2000, requesterBalance);  // received
    }

    /* ================= TEST 3 ================= */
    @Test
    void testDeclineMoneyRequest() throws Exception {

        requestService.createRequest(
                requesterId,
                payerId,
                1500
        );

        MoneyRequest request =
                requestService.getIncomingRequests(payerId).get(0);

        requestService.declineRequest(
                request.getRequestId(),
                "Not now"
        );

        double payerBalance =
                walletService.getWallet(payerId).getBalance();

        double requesterBalance =
                walletService.getWallet(requesterId).getBalance();

        assertEquals(5000, payerBalance);     // unchanged
        assertEquals(0, requesterBalance);    // unchanged
    }
}
