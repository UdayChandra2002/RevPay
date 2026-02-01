package com.revpay.util;

import java.security.SecureRandom;

public class OTPUtil {

    private static final SecureRandom random = new SecureRandom();

    private OTPUtil() {}

    public static String generateOTP() {
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
