package com.antonjohansson.lprs.controller.configuration;

import static com.antonjohansson.lprs.controller.configuration.Configuration.DOMAIN;
import static com.antonjohansson.lprs.controller.configuration.Configuration.PASSWORD;
import static com.antonjohansson.lprs.controller.configuration.Configuration.PROVIDER_URL;
import static com.antonjohansson.lprs.controller.configuration.Configuration.USERNAME;

import com.google.inject.Inject;

/**
 * Validates required properties in the configuration.
 */
class ConfigurationValidator
{
    private final Configuration configuration;

    @Inject
    ConfigurationValidator(Configuration configuration)
    {
        this.configuration = configuration;
        validate();
    }

    private void validate()
    {
        assertExists(PROVIDER_URL);
        assertExists(DOMAIN);
        assertExists(USERNAME);
        assertExists(PASSWORD);
    }

    private void assertExists(String key)
    {
        if (!configuration.get(key).isPresent())
        {
            throw new IllegalStateException("The key '" + key + "' is required in the configuration");
        }
    }
}
