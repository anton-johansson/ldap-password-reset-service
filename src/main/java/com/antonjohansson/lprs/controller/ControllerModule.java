package com.antonjohansson.lprs.controller;

import static com.google.inject.Scopes.SINGLETON;

import com.google.inject.AbstractModule;

/**
 * Contains IOC-bindings for the controllers.
 */
public class ControllerModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(ILdapFactory.class).to(LdapFactory.class).in(SINGLETON);
    }
}
