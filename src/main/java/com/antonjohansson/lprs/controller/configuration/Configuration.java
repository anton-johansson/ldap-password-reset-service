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
package com.antonjohansson.lprs.controller.configuration;

import static java.util.stream.Collectors.toSet;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

/**
 * Contains application configuration.
 */
public final class Configuration
{
    private static final int DEFAULT_PORT = 8080;
    static final String PORT = "port";
    static final String PROVIDER_URL = "provider-url";
    static final String DOMAIN = "domain";
    static final String USERNAME = "username";
    static final String PASSWORD = "password";

    private final Map<String, String> configuration;

    Configuration(Map<String, String> configuration)
    {
        this.configuration = new HashMap<>(configuration);
    }

    /**
     * Gets the web server port.
     */
    public int getPort()
    {
        int port = DEFAULT_PORT;
        String portValue = configuration.get(PORT);
        if (portValue == null)
        {
            return port;
        }
        try
        {
            port = Integer.parseInt(portValue);
        }
        catch (NumberFormatException e)
        {
            throw new RuntimeException(e);
        }
        return port;
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
     * Gets a value, or a default value if the given key does not exist.
     *
     * @param key The key to get value for.
     * @param defaultValue The default value to fall back to.
     * @return Returns the value.
     */
    public String getOrDefault(String key, String defaultValue)
    {
        return configuration.getOrDefault(key, defaultValue);
    }

    /**
     * Gets a boolean value from the configuration.
     *
     * @param key The key to get boolean value for.
     */
    public boolean getBoolean(String key)
    {
        return Boolean.valueOf(configuration.get(key));
    }

    /**
     * Returns a set of sub-properties for a given prefix.
     *
     * @param prefix The prefix to get sub-properties for.
     * @return Returns all the sub-properties.
     */
    public Set<Entry<String, String>> getSubProperties(String prefix)
    {
        return configuration
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().startsWith(prefix))
                .map(entry -> new AbstractMap.SimpleEntry<>(
                        entry.getKey().substring(prefix.length()),
                        entry.getValue()))
                .collect(toSet());
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
