package com.revpay.menu;

import com.revpay.model.MoneyRequest;
import com.revpay.model.User;
import com.revpay.service.MoneyRequestService;
import com.revpay.service.UserService;
import com.revpay.util.OTPUtil;
import com.revpay.util.PasswordUtil;

import java.util.List;
import java.util.Scanner;

public class RequestMoneyMenu {

    private final User user;

    public RequestMoneyMenu(User user) {
        this.user = user;
    }

    public void show() {

        Scanner sc = new Scanner(System.in);
        MoneyRequestService requestService = new MoneyRequestService();
        UserService userService = new UserService();

        System.out.println("""
            ===== REQUEST MONEY =====
            1. Create Money Request
            2. View Incoming Requests
            3. View Sent Requests
            4. Back
        """);

        int choice = sc.nextInt();
        sc.nextLine();

        try {
            switch (choice) {

                /* ================= CREATE REQUEST ================= */
                case 1 -> {
                    System.out.println("Select receiver lookup:");
                    System.out.println("1. User ID");
                    System.out.println("2. Email / Phone");
                    int opt = sc.nextInt();
                    sc.nextLine();

                    User receiver;

                    if (opt == 1) {
                        System.out.print("Enter User ID: ");
                        receiver = userService.getUserById(sc.nextInt());
                        sc.nextLine();
                    } else {
                        System.out.print("Enter Email / Phone: ");
                        receiver = userService.getUserByEmailOrPhone(sc.nextLine());
                    }

                    if (receiver == null) {
                        System.out.println("User not found.");
                        return;
                    }

                    System.out.print("Enter amount to request: ");
                    double amount = sc.nextDouble();
                    sc.nextLine();

                    requestService.createRequest(
                            user.getUserId(),
                            receiver.getUserId(),
                            amount
                    );

                    System.out.println("Money request sent successfully!");
                }

                /* ================= INCOMING REQUESTS ================= */
                case 2 -> {
                    List<MoneyRequest> requests =
                            requestService.getIncomingRequests(user.getUserId());

                    if (requests.isEmpty()) {
                        System.out.println("No incoming requests.");
                        return;
                    }

                    for (int i = 0; i < requests.size(); i++) {
                        MoneyRequest r = requests.get(i);
                        System.out.println((i + 1) +
                                ". From User " + r.getSenderId() +
                                " | Amount: ₹" + r.getAmount());
                    }

                    System.out.print("Select request: ");
                    int idx = sc.nextInt() - 1;
                    sc.nextLine();

                    if (idx < 0 || idx >= requests.size()) {
                        System.out.println("Invalid selection.");
                        return;
                    }

                    MoneyRequest req = requests.get(idx);

                    System.out.println("1. Accept");
                    System.out.println("2. Decline");
                    int action = sc.nextInt();
                    sc.nextLine();

                    if (action == 1) {

                        System.out.print("Enter Transaction PIN: ");
                        String pin = sc.nextLine();

                        if (!PasswordUtil.verify(pin, user.getTransactionPinHash())) {
                            System.out.println("Invalid PIN.");
                            return;
                        }

                        String otp = OTPUtil.generateOTP();
                        System.out.println("Security code: " + otp);
                        System.out.print("Enter security code: ");

                        if (!otp.equals(sc.nextLine())) {
                            System.out.println("Invalid OTP.");
                            return;
                        }

                        requestService.acceptRequest(req.getRequestId());
                        System.out.println("Request accepted and money transferred.");

                    } else if (action == 2) {

                        System.out.print("Enter decline reason (optional): ");
                        String reason = sc.nextLine();

                        requestService.declineRequest(req.getRequestId(), reason);
                        System.out.println("Request declined.");
                    }
                }

                /* ================= SENT REQUESTS ================= */
                case 3 -> {
                    List<MoneyRequest> sent =
                            requestService.getSentRequests(user.getUserId());

                    if (sent.isEmpty()) {
                        System.out.println("No sent requests.");
                        return;
                    }

                    sent.forEach(r ->
                            System.out.println("To User " +
                                    r.getReceiverId() +
                                    " | ₹" + r.getAmount() +
                                    " | " + r.getStatus()));
                }

                case 4 -> {
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
