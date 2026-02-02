package com.revpay.service;

import com.revpay.dao.UserDAO;
import com.revpay.model.SecurityQuestion;
import com.revpay.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionServiceTest {

    private final TransactionService transactionService =
            new TransactionService();

    private final WalletService walletService =
            new WalletService();

    private final UserDAO userDAO = new UserDAO();

    @Test
    void testSendMoney() throws Exception {

        AuthService authService = new AuthService();

        /* ================= SENDER ================= */
        User sender = new User();
        sender.setFullName("Sender");
        sender.setEmail("sender_" + System.currentTimeMillis() + "@mail.com");
        sender.setPhone("7" + (int) (Math.random() * 1000000000));
        sender.setUserType("PERSONAL");

        SecurityQuestion sq1 = new SecurityQuestion();
        sq1.setQuestion("Q?");
        sq1.setAnswerHash("A");

        int senderId = authService.registerUser(
                sender,
                "pass123",
                "1234",
                sq1,
                null   //  PERSONAL USER → no business profile
        );

        /* ================= RECEIVER ================= */
        User receiver = new User();
        receiver.setFullName("Receiver");
        receiver.setEmail("receiver_" + System.currentTimeMillis() + "@mail.com");
        receiver.setPhone("6" + (int) (Math.random() * 1000000000));
        receiver.setUserType("PERSONAL");

        SecurityQuestion sq2 = new SecurityQuestion();
        sq2.setQuestion("Q?");
        sq2.setAnswerHash("A");

        int receiverId = authService.registerUser(
                receiver,
                "pass123",
                "1234",
                sq2,
                null   //  PERSONAL USER
        );

        /* ================= ADD MONEY ================= */
        WalletService walletService = new WalletService();
        walletService.addMoney(senderId, 1000);

        /* ================= SEND MONEY ================= */
        TransactionService transactionService = new TransactionService();
        transactionService.sendMoney(senderId, receiverId, 300);

        double senderBalance =
                walletService.getWallet(senderId).getBalance();

        double receiverBalance =
                walletService.getWallet(receiverId).getBalance();

        assertEquals(700, senderBalance);
        assertEquals(300, receiverBalance);
    }
}

