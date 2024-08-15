package com.pnimac.urlshortner.util;

import java.security.SecureRandom;
import java.util.Base64;

public class SaltGenerator {

    private static final SecureRandom random = new SecureRandom();

    public static String generateSalt() {
        byte[] saltBytes = new byte[16]; // Generate 16 bytes salt
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes); // Encode to Base64 for readability
    }
}
