package com.antonjohansson.lprs.controller;

/**
 * Factory for creating {@link Ldap LDAP instances}.
 */
public interface ILdapFactory
{
    /**
     * Gets the {@link Ldap LDAP}.
     *
     * @return Returns the {@link Ldap LDAP}.
     */
    Ldap getLdap();
}
