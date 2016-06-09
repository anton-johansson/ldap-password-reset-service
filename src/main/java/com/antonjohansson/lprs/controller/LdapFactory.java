package com.antonjohansson.lprs.controller;

/**
 * Default implementation of {@link ILdapFactory}.
 */
class LdapFactory implements ILdapFactory
{
    @Override
    public Ldap getLdap()
    {
        return new Ldap("X", "Y", "Z", "W");
    }
}
