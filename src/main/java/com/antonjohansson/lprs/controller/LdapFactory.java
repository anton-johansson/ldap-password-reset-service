package com.antonjohansson.lprs.controller;

import com.antonjohansson.lprs.controller.configuration.Configuration;
import com.google.inject.Inject;

/**
 * Default implementation of {@link ILdapFactory}.
 */
class LdapFactory implements ILdapFactory
{
    private final Configuration configuration;

    @Inject
    LdapFactory(Configuration configuration)
    {
        this.configuration = configuration;
    }

    @Override
    public Ldap getLdap()
    {
        return new Ldap(
                configuration.getProviderURL(),
                configuration.getDomain(),
                configuration.getUsername(),
                configuration.getPassword());
    }
}
