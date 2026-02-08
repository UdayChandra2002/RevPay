package com.revpay.service;

import com.revpay.model.SecurityQuestion;
import com.revpay.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    private AuthService authService;
    private String email;
    private int userId;

    @BeforeEach
    void setup() throws Exception {

        authService = new AuthService();

        User user = new User();
        user.setFullName("Auth Test User");
        email = "auth_" + System.currentTimeMillis() + "@mail.com";
        user.setEmail(email);
        user.setPhone("9" + (int)(Math.random() * 1000000000));
        user.setUserType("PERSONAL");

        SecurityQuestion sq = new SecurityQuestion();
        sq.setQuestion("Fav color?");
        sq.setAnswerHash("blue");

        // ✅ Create user + wallet
        userId = authService.registerUser(
                user,
                "password123",
                "1234",
                sq,
                null   // personal user
        );
    }

    /* ================= TEST 1 ================= */
    @Test
    void testLoginSuccess() throws Exception {

        User loggedInUser =
                authService.login(email, "password123");

        assertNotNull(loggedInUser);
        assertEquals(email, loggedInUser.getEmail());
    }

    /* ================= TEST 2 ================= */
    @Test
    void testLoginWrongPassword() {

        Exception ex = assertThrows(
                Exception.class,
                () -> authService.login(email, "wrongpass")
        );

        assertEquals("Invalid password", ex.getMessage());
    }

    /* ================= TEST 3 ================= */
    @Test
    void testLoginUserNotFound() throws Exception {

        User user =
                authService.login("notfound@mail.com", "password123");

        assertNull(user);
    }

    /* ================= TEST 4 ================= */
    @Test
    void testAccountLockedAfterMultipleFailures() {

        for (int i = 0; i < 3; i++) {
            try {
                authService.login(email, "wrongpass");
            } catch (Exception ignored) {}
        }

        Exception ex = assertThrows(
                Exception.class,
                () -> authService.login(email, "password123")
        );

        assertEquals("Account is locked", ex.getMessage());
    }
}
