package com.antonjohansson.lprs.controller.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Contains application configuration.
 */
public final class Configuration
{
    static final String PROVIDER_URL = "providerURL";
    static final String DOMAIN = "domain";
    static final String USERNAME = "username";
    static final String PASSWORD = "password";

    private final Map<String, String> configuration;

    Configuration(Map<String, String> configuration)
    {
        this.configuration = new HashMap<>(configuration);
    }

    public String getProviderURL()
    {
        return configuration.get(PROVIDER_URL);
    }

    public String getDomain()
    {
        return configuration.get(DOMAIN);
    }

    public String getUsername()
    {
        return configuration.get(USERNAME);
    }

    public String getPassword()
    {
        return configuration.get(PASSWORD);
    }

    /**
     * Gets an optional {@link String string} value from the configuration.
     *
     * @param key The key of the value.
     * @return Returns the value.
     */
    public Optional<String> get(String key)
    {
        return Optional.ofNullable(configuration.get(key));
    }
}
