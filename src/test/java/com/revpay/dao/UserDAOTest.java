package com.revpay.dao;

import com.revpay.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    private final UserDAO userDAO = new UserDAO();

    @Test
    void testCreateAndFetchUser() throws Exception {

        User user = new User();
        user.setFullName("Test User");
        user.setEmail("testuser_" + System.currentTimeMillis() + "@mail.com");
        user.setPhone("9" + (int)(Math.random() * 1000000000));
        user.setPasswordHash("hashed_pass");
        user.setTransactionPinHash("hashed_pin");
        user.setUserType("PERSONAL");

        int userId = userDAO.createUser(user);

        assertTrue(userId > 0, "User ID should be generated");

        User fetched =
                userDAO.getUserByEmailOrPhone(user.getEmail());

        assertNotNull(fetched, "Fetched user should not be null");
        assertEquals(user.getEmail(), fetched.getEmail());
        assertEquals("PERSONAL", fetched.getUserType());
    }
}
