package com.revpay.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtil {

    // 🔐 16-byte secret key (AES-128)
    // MUST be exactly 16 characters
    private static final String SECRET_KEY = "RevPaySecretKey!";

    private static final String ALGORITHM = "AES";

    private EncryptionUtil() {
    }

    public static String encrypt(String plainText) {

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            SecretKeySpec keySpec =
                    new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);

            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            byte[] encryptedBytes =
                    cipher.doFinal(plainText.getBytes());

            return Base64.getEncoder().encodeToString(encryptedBytes);

        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }

    public static String decrypt(String encryptedText) {

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            SecretKeySpec keySpec =
                    new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);

            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            byte[] decodedBytes =
                    Base64.getDecoder().decode(encryptedText);

            byte[] decryptedBytes =
                    cipher.doFinal(decodedBytes);

            return new String(decryptedBytes);

        } catch (Exception e) {
            throw new RuntimeException("Decryption failed", e);
        }
    }
}
