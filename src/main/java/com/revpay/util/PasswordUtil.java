package com.revpay.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    private PasswordUtil() {}

    public static String hash(String plainText) {
        return BCrypt.hashpw(plainText, BCrypt.gensalt());
    }

    public static boolean verify(String plainText, String hash) {
        if (plainText == null || hash == null) {
            return false;
        }
        return BCrypt.checkpw(plainText, hash);
    }
}
