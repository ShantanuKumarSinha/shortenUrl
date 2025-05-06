package com.shann.shortenurl.utils;


import java.security.SecureRandom;
import java.util.Random;

/**
 * Utility class for generating short URLs.
 */

public class ShortUrlGenerator {

    private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int SHORT_URL_LENGTH = 6;

    /**
     * Generates a random short URL of fixed length.
     *
     * @return A randomly generated short URL.
     */
    public static String generateShortUrl() {
        Random random = new SecureRandom();
        StringBuilder shortUrl = new StringBuilder(SHORT_URL_LENGTH);

        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            int index = random.nextInt(ALLOWED_CHARACTERS.length());
            shortUrl.append(ALLOWED_CHARACTERS.charAt(index));
        }

        return shortUrl.toString();
    }
}
