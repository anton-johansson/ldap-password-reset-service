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

import static com.antonjohansson.lprs.controller.configuration.Configuration.DOMAIN;
import static com.antonjohansson.lprs.controller.configuration.Configuration.PASSWORD;
import static com.antonjohansson.lprs.controller.configuration.Configuration.PROVIDER_URL;
import static com.antonjohansson.lprs.controller.configuration.Configuration.USERNAME;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.antonjohansson.lprs.controller.validation.IValidationModel;
import com.google.inject.Inject;

/**
 * Validates required properties in the configuration.
 */
class ConfigurationValidator
{
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationValidator.class);

    private final Configuration configuration;
    private final IValidationModel model;

    @Inject
    ConfigurationValidator(Configuration configuration, IValidationModel model)
    {
        this.configuration = configuration;
        this.model = model;
        validate();
    }

    private void validate()
    {
        assertExists(PROVIDER_URL);
        assertExists(DOMAIN);
        assertExists(USERNAME);
        assertExists(PASSWORD);

        LOG.info("Configuration validated");
    }

    private void assertExists(String key)
    {
        if (!configuration.get(key).isPresent())
        {
            String message = "The key '" + key + "' is required in the configuration";
            LOG.error(message);
            model.addValidationError(message);
        }
    }
}
