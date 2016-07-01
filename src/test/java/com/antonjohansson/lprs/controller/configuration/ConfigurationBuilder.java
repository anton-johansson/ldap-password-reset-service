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

import java.util.HashMap;
import java.util.Map;

/**
 * Builds {@link Configuration configurations} for unit tests.
 */
public class ConfigurationBuilder
{
    private final Map<String, String> configuration = new HashMap<>();

    /**
     * Constructs a new {@link ConfigurationBuilder}.
     */
    private ConfigurationBuilder()
    {
    }

    /**
     * Starts building a new {@link Configuration}.
     */
    public static ConfigurationBuilder configuration()
    {
        return new ConfigurationBuilder();
    }

    /**
     * Adds a new value.
     *
     * @param key The key of the value.
     * @param value The value to add.
     */
    public ConfigurationBuilder value(String key, String value)
    {
        configuration.put(key, value);
        return this;
    }

    /**
     * Builds the {@link Configuration configuration}.
     */
    public Configuration build()
    {
        return new Configuration(configuration);
    }
}
