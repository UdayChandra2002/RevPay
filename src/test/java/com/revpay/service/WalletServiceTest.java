package com.revpay.service;

import com.revpay.dao.UserDAO;
import com.revpay.model.User;
import com.revpay.util.PasswordUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WalletServiceTest {

    private final WalletService walletService = new WalletService();
    private final UserDAO userDAO = new UserDAO();

    @Test
    void testAddMoney() throws Exception {

        User user = new User();
        user.setFullName("Wallet Test");
        user.setEmail("wallet_" + System.currentTimeMillis() + "@mail.com");
        user.setPhone("8" + (int)(Math.random() * 1000000000));
        user.setPasswordHash(PasswordUtil.hash("pass123"));
        user.setTransactionPinHash(PasswordUtil.hash("1234"));
        user.setUserType("PERSONAL");

        int userId = userDAO.createUser(user);

        walletService.addMoney(userId, 500);

        double balance =
                walletService.getWallet(userId).getBalance();

        assertEquals(500, balance);
    }
}
