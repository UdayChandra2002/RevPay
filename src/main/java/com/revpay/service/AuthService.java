package com.revpay.service;

import com.revpay.dao.BusinessProfileDAO;
import com.revpay.model.BusinessProfile;
import com.revpay.dao.SecurityQuestionDAO;
import com.revpay.dao.UserDAO;
import com.revpay.dao.WalletDAO;
import com.revpay.model.SecurityQuestion;
import com.revpay.model.User;
import com.revpay.util.PasswordUtil;

import java.util.Scanner;

public class AuthService {

    private final UserDAO userDAO = new UserDAO();
    private final WalletDAO walletDAO = new WalletDAO();
    private final SecurityQuestionDAO securityQuestionDAO = new SecurityQuestionDAO();

    /* ================= REGISTER ================= */

    public int registerUser(
            User user,
            String plainPassword,
            String plainPin,
            SecurityQuestion sq,
            BusinessProfile businessProfile   //  NEW
    ) throws Exception {

        user.setPasswordHash(PasswordUtil.hash(plainPassword));
        user.setTransactionPinHash(PasswordUtil.hash(plainPin));

        int userId = userDAO.createUser(user);

        // wallet
        walletDAO.createWallet(userId);

        // security question
        sq.setUserId(userId);
        sq.setAnswerHash(PasswordUtil.hash(sq.getAnswerHash()));
        securityQuestionDAO.saveQuestion(sq);

        //  ONLY FOR BUSINESS USERS
        if ("BUSINESS".equals(user.getUserType())) {
            businessProfile.setBusinessId(userId);

            BusinessProfileDAO bpDAO = new BusinessProfileDAO();
            bpDAO.saveBusinessProfile(businessProfile);
        }

        return userId;
    }


    /* ================= LOGIN ================= */

    public User login(String emailOrPhone, String password) throws Exception {

        User user = userDAO.getUserByEmailOrPhone(emailOrPhone);

        // User not found
        if (user == null) {
            return null;
        }

        // Account locked
        if ("Y".equals(user.getIsLocked())) {
            throw new Exception("Account is locked");
        }

        // Password incorrect
        if (!PasswordUtil.verify(password, user.getPasswordHash())) {

            int attempts = user.getFailedAttempts() + 1;
            userDAO.updateFailedAttempts(user.getUserId(), attempts);

            if (attempts >= 3) {
                userDAO.lockUser(user.getUserId());
                throw new Exception(
                        "Account locked due to multiple failed attempts"
                );
            }

            throw new Exception("Invalid password");
        }

        // Login success → reset attempts
        userDAO.updateFailedAttempts(user.getUserId(), 0);
        // 🔹 Update last login timestamp
        userDAO.updateLastLogin(user.getUserId());

        return user;
    }

    /* ================= FORGOT PASSWORD ================= */

    public void forgotPassword(String emailOrPhone,
                               String securityAnswer,
                               String newPassword,
                               String confirmPassword) throws Exception {

        User user = userDAO.getUserByEmailOrPhone(emailOrPhone);

        if (user == null) {
            throw new Exception("User not found");
        }

        SecurityQuestion sq =
                securityQuestionDAO.getByUserId(user.getUserId());

        if (!PasswordUtil.verify(securityAnswer, sq.getAnswerHash())) {
            throw new Exception("Security answer incorrect");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new Exception("Passwords do not match");
        }

        String newHash = PasswordUtil.hash(newPassword);

        // Reset password + unlock account
        userDAO.resetPassword(user.getUserId(), newHash);
    }


    public void forgotPasswordFlow(String emailOrPhone) throws Exception {

        User user = userDAO.getUserByEmailOrPhone(emailOrPhone);

        if (user == null) {
            throw new Exception("User not found.");
        }

        SecurityQuestion sq =
                securityQuestionDAO.getByUserId(user.getUserId());

        if (sq == null) {
            throw new Exception("Security question not found.");
        }

        Scanner sc = new Scanner(System.in);

        System.out.println("Security Question:");
        System.out.println(sq.getQuestion());

        System.out.print("Answer: ");
        String answer = sc.nextLine();

        if (!PasswordUtil.verify(answer, sq.getAnswerHash())) {
            throw new Exception("Incorrect security answer.");
        }

        System.out.print("Enter new password: ");
        String newPassword = sc.nextLine();

        System.out.print("Confirm new password: ");
        String confirm = sc.nextLine();

        if (!newPassword.equals(confirm)) {
            throw new Exception("Passwords do not match.");
        }

        String newHash = PasswordUtil.hash(newPassword);
        userDAO.resetPassword(user.getUserId(), newHash);

        System.out.println("Password reset successful. Account unlocked.");
    }



}
