package com.revpay.menu;

import com.revpay.model.BusinessProfile;
import com.revpay.model.SecurityQuestion;
import com.revpay.model.User;
import com.revpay.service.AuthService;

import java.util.Scanner;

public class RegisterMenu {

    public void show() {

        Scanner sc = new Scanner(System.in);
        AuthService authService = new AuthService();

        System.out.println("""
            Select Account Type:
            1. Personal
            2. Business
            """);

        int type = sc.nextInt();
        sc.nextLine();

        User user = new User();

        /* ================= COMMON USER DETAILS ================= */

        System.out.print("Full Name: ");
        user.setFullName(sc.nextLine());

        System.out.print("Email: ");
        user.setEmail(sc.nextLine());

        System.out.print("Phone: ");
        user.setPhone(sc.nextLine());

        System.out.print("Create Login Password: ");
        String password = sc.nextLine();

        System.out.print("Create Transaction PIN: ");
        String pin = sc.nextLine();

        user.setUserType(type == 1 ? "PERSONAL" : "BUSINESS");

        /* ================= SECURITY QUESTION ================= */

        System.out.print("Security Question: ");
        String question = sc.nextLine();

        System.out.print("Security Answer: ");
        String answer = sc.nextLine();

        SecurityQuestion sq = new SecurityQuestion();
        sq.setQuestion(question);
        sq.setAnswerHash(answer);

        /* ================= BUSINESS DETAILS (ONLY IF BUSINESS) ================= */

        BusinessProfile bp = null;

        if (type == 2) {
            bp = new BusinessProfile();

            System.out.println("\n--- Business Details ---");

            System.out.print("Business Name: ");
            bp.setBusinessName(sc.nextLine());

            System.out.print("Business Type: ");
            bp.setBusinessType(sc.nextLine());

            System.out.print("Tax ID / PAN: ");
            bp.setTaxId(sc.nextLine());

            System.out.print("Business Address: ");
            bp.setAddress(sc.nextLine());
        }

        /* ================= REGISTER ================= */

        try {
            int userId = authService.registerUser(
                    user,
                    password,
                    pin,
                    sq,
                    bp   // 👈 null for personal, object for business
            );

            System.out.println("Registration successful! User ID: " + userId);

        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }
}
