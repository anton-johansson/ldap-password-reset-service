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
package com.antonjohansson.lprs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.antonjohansson.lprs.controller.configuration.Configuration;
import com.google.inject.Inject;

/**
 * Default implementation of {@link ILdapFactory}.
 */
class LdapFactory implements ILdapFactory
{
    private static final Logger LOG = LoggerFactory.getLogger(LdapFactory.class);

    private final Configuration configuration;

    @Inject
    LdapFactory(Configuration configuration)
    {
        this.configuration = configuration;
    }

    @Override
    public Ldap getLdap()
    {
        LOG.debug("Creating a connection to the LDAP server");
        return new Ldap(
                configuration.getProviderURL(),
                configuration.getDomain(),
                configuration.getUsername(),
                configuration.getPassword());
    }
}
