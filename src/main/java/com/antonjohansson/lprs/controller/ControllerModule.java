package com.antonjohansson.lprs.controller;

import static com.google.inject.Scopes.SINGLETON;

import com.antonjohansson.lprs.controller.configuration.ConfigurationModule;
import com.antonjohansson.lprs.controller.validation.ValidationModule;
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
        install(new ConfigurationModule());
        install(new ValidationModule());
    }
}
