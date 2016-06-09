package com.antonjohansson.lprs.controller.token;

import com.antonjohansson.lprs.model.User;

/**
 * Sends tokens to user.
 */
public interface ITokenSender
{
    /**
     * Sends a token to a user.
     *
     * @param user The user to send the token to.
     * @param token The token to send.
     */
    void send(User user, String token);
}
