package com.revpay;

import com.revpay.dao.UserDAO;
import com.revpay.model.User;
import com.revpay.service.TransactionService;
import com.revpay.service.WalletService;
import com.revpay.util.PasswordUtil;
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

        User sender = new User();
        sender.setFullName("Sender");
        sender.setEmail("sender_" + System.currentTimeMillis() + "@mail.com");
        sender.setPhone("7" + (int)(Math.random() * 1000000000));
        sender.setPasswordHash(PasswordUtil.hash("pass123"));
        sender.setTransactionPinHash(PasswordUtil.hash("1234"));
        sender.setUserType("PERSONAL");

        int senderId = userDAO.createUser(sender);

        User receiver = new User();
        receiver.setFullName("Receiver");
        receiver.setEmail("receiver_" + System.currentTimeMillis() + "@mail.com");
        receiver.setPhone("6" + (int)(Math.random() * 1000000000));
        receiver.setPasswordHash(PasswordUtil.hash("pass123"));
        receiver.setTransactionPinHash(PasswordUtil.hash("1234"));
        receiver.setUserType("PERSONAL");

        int receiverId = userDAO.createUser(receiver);

        walletService.addMoney(senderId, 1000);

        transactionService.sendMoney(senderId, receiverId, 300);

        double senderBalance =
                walletService.getWallet(senderId).getBalance();

        double receiverBalance =
                walletService.getWallet(receiverId).getBalance();

        assertEquals(700, senderBalance);
        assertEquals(300, receiverBalance);
    }
}
