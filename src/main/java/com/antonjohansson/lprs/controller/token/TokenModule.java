package com.antonjohansson.lprs.controller.token;

import com.google.inject.AbstractModule;

/**
 * Contains IOC bindings for the token module.
 */
public class TokenModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(ITokenSender.class).toProvider(TokenSenderProvider.class);
    }
}
