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
