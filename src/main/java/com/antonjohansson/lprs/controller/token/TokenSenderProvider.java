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
package com.antonjohansson.lprs.controller.token;

import java.util.Optional;
import java.util.function.Supplier;

import org.apache.commons.beanutils.PropertyUtils;
import org.reflections.Reflections;

import com.antonjohansson.lprs.controller.configuration.Configuration;
import com.antonjohansson.lprs.controller.validation.IValidationModel;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Provides the {@link ITokenSender} implementation.
 */
class TokenSenderProvider implements Provider<ITokenSender>
{
    private final Configuration configuration;
    private final IValidationModel validationModel;

    @Inject
    TokenSenderProvider(Configuration configuration, IValidationModel validationModel)
    {
        this.configuration = configuration;
        this.validationModel = validationModel;
    }

    private String getTokenSenderName()
    {
        return configuration.getOrDefault("token-sender", ConsoleTokenSender.class.getSimpleName());
    }

    @Override
    public ITokenSender get()
    {
        Supplier<ITokenSender> consoleTokenSenderSupplier = () ->
        {
            validationModel.addValidationError("Could not find token sender implementation with name '" + getTokenSenderName() + "'");
            return new ConsoleTokenSender();
        };

        return getTokenSender().orElseGet(consoleTokenSenderSupplier);
    }

    private Optional<ITokenSender> getTokenSender()
    {
        return new Reflections(getClass().getPackage().getName())
                .getSubTypesOf(ITokenSender.class)
                .stream()
                .filter(type -> type.getSimpleName().equals(getTokenSenderName()))
                .findAny()
                .map(this::newInstance);
    }

    private ITokenSender newInstance(Class<? extends ITokenSender> type)
    {
        try
        {
            ITokenSender instance = type.newInstance();
            setProperties(instance);
            return instance;
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            validationModel.addValidationError("Could not create token sender instance: " + e.getMessage());
            return new ConsoleTokenSender();
        }
    }

    private void setProperties(ITokenSender instance)
    {
        configuration
                .getSubProperties("token-sender.")
                .stream()
                .forEach(entry ->
                {
                    try
                    {
                        PropertyUtils.setProperty(instance, entry.getKey(), entry.getValue());
                    }
                    catch (Exception e)
                    {
                        // LOG
                    }
                });
    }
}
