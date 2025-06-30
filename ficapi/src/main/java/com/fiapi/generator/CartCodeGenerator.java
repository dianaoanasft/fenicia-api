package com.fiapi.generator;

import java.security.SecureRandom;

public class CartCodeGenerator {

    private static final SecureRandom secureRandom = new SecureRandom();

    public String generateCartCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int randomDigit = secureRandom.nextInt(10); // Generates a random digit between 0 and 9
            code.append(randomDigit);
        }
        return code.toString();
    }

}