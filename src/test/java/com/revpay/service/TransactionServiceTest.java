package com.revpay.service;

import com.revpay.model.SecurityQuestion;
import com.revpay.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    private AuthService authService;
    private WalletService walletService;
    private TransactionService transactionService;

    private int senderId;
    private int receiverId;

    @BeforeEach
    void setup() throws Exception {

        authService = new AuthService();
        walletService = new WalletService();
        transactionService = new TransactionService();

        /* ---------- SENDER ---------- */
        User sender = new User();
        sender.setFullName("Sender");
        sender.setEmail("sender_" + System.currentTimeMillis() + "@mail.com");
        sender.setPhone("9" + (int)(Math.random() * 1000000000));
        sender.setUserType("PERSONAL");

        SecurityQuestion sq1 = new SecurityQuestion();
        sq1.setQuestion("Fav color?");
        sq1.setAnswerHash("blue");

        senderId = authService.registerUser(
                sender,
                "password123",
                "1234",
                sq1,
                null
        );

        /* ---------- RECEIVER ---------- */
        User receiver = new User();
        receiver.setFullName("Receiver");
        receiver.setEmail("receiver_" + System.currentTimeMillis() + "@mail.com");
        receiver.setPhone("8" + (int)(Math.random() * 1000000000));
        receiver.setUserType("PERSONAL");

        SecurityQuestion sq2 = new SecurityQuestion();
        sq2.setQuestion("Fav city?");
        sq2.setAnswerHash("blr");

        receiverId = authService.registerUser(
                receiver,
                "password123",
                "1234",
                sq2,
                null
        );

        // Add money to sender wallet
        walletService.addMoney(senderId, 5000);
    }

    /* ================= TEST 1 ================= */
    @Test
    void testSendMoneySuccess() throws Exception {

        transactionService.sendMoney(senderId, receiverId, 2000);

        double senderBalance =
                walletService.getWallet(senderId).getBalance();

        double receiverBalance =
                walletService.getWallet(receiverId).getBalance();

        assertEquals(3000, senderBalance);
        assertEquals(2000, receiverBalance);
    }

    /* ================= TEST 2 ================= */
    @Test
    void testSendMoneyInsufficientBalance() {

        Exception ex = assertThrows(
                Exception.class,
                () -> transactionService.sendMoney(senderId, receiverId, 8000)
        );

        assertEquals("Insufficient balance.", ex.getMessage());
    }

    /* ================= TEST 3 ================= */
    @Test
    void testSendMoneyToSelf() {

        Exception ex = assertThrows(
                Exception.class,
                () -> transactionService.sendMoney(senderId, senderId, 500)
        );

        assertEquals(
                "Sender and receiver cannot be the same.",
                ex.getMessage()
        );
    }

    /* ================= TEST 4 ================= */
    @Test
    void testSendMoneyInvalidAmount() {

        Exception ex = assertThrows(
                Exception.class,
                () -> transactionService.sendMoney(senderId, receiverId, -100)
        );

        assertEquals(
                "Amount must be greater than zero.",
                ex.getMessage()
        );
    }
}
