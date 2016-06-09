package com.antonjohansson.lprs.controller.token;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Generates tokens.
 */
public final class TokenGenerator
{
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int TOKEN_LENGTH = 5;
    private static final Random RANDOM = new SecureRandom();

    private TokenGenerator()
    {
    }

    /**
     * Generates a new token.
     *
     * @return Returns the generated token.
     */
    public static String generate()
    {
        StringBuilder builder = new StringBuilder(TOKEN_LENGTH);
        for (int i = 0; i < TOKEN_LENGTH; i++)
        {
            int index = RANDOM.nextInt(CHARACTERS.length());
            char character = CHARACTERS.charAt(index);
            builder.append(character);
        }
        return builder.toString();
    }
}
