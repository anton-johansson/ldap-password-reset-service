/**
 * Copyright (c) Anton Johansson <antoon.johansson@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
