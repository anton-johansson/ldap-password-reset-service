package com.antonjohansson.lprs.controller.token;

import com.antonjohansson.lprs.model.User;

/**
 * {@link ITokenSender} implementation that prints the token to the console.
 */
public class ConsoleTokenSender implements ITokenSender
{
    @Override
    public void send(User user, String token)
    {
        System.out.println("Generated token '" + token + "'.");
    }
}
