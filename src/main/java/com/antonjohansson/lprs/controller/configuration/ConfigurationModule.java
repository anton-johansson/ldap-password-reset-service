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

import static java.util.stream.Collectors.toMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * Provides the {@link Configuration configuration}.
 */
public class ConfigurationModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(ConfigurationValidator.class).asEagerSingleton();
    }

    @Provides
    Configuration configuration()
    {
        File configurationFile = getConfigurationFile();
        try (InputStream stream = new FileInputStream(configurationFile))
        {
            Properties properties = new Properties();
            properties.load(stream);

            return new Configuration(
                    properties.entrySet()
                    .stream()
                    .collect(toMap(
                            entry -> (String) entry.getKey(),
                            entry -> (String) entry.getValue())));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private File getConfigurationFile()
    {
        return Optional.ofNullable(System.getProperty("configurationFile"))
                .map(File::new)
                .filter(File::exists)
                .filter(File::isFile)
                .orElseThrow(() -> new IllegalStateException("An existing 'configurationFile' application argument is required"));
    }
}
