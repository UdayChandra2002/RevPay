package com.revpay.service;

import com.revpay.model.SecurityQuestion;
import com.revpay.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WalletServiceTest {

    private final WalletService walletService = new WalletService();
    private final AuthService authService = new AuthService();

    @Test
    void testAddMoney() throws Exception {

        User user = new User();
        user.setFullName("Wallet Test");
        user.setEmail("wallet_" + System.currentTimeMillis() + "@mail.com");
        user.setPhone("8" + (int)(Math.random() * 1000000000));
        user.setUserType("PERSONAL");

        SecurityQuestion sq = new SecurityQuestion();
        sq.setQuestion("Test question?");
        sq.setAnswerHash("test");

        //  REGISTER USER PROPERLY (creates wallet)
        int userId = authService.registerUser(
                user,
                "pass123",
                "1234",
                sq,
                null   // personal user → no business profile
        );

        //  ADD MONEY
        walletService.addMoney(userId, 500);

        double balance =
                walletService.getWallet(userId).getBalance();

        assertEquals(500, balance);
    }
}
