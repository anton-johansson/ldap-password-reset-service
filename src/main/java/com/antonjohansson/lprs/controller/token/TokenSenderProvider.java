package com.antonjohansson.lprs.controller.token;

import java.util.Optional;
import java.util.function.Supplier;

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

    @Override
    public ITokenSender get()
    {
        Supplier<ITokenSender> consoleTokenSenderSupplier = () ->
        {
            validationModel.addValidationError("Could not find token sender implementation with name '" + configuration.getTokenSender() + "'");
            return new ConsoleTokenSender();
        };

        return getTokenSender().orElseGet(consoleTokenSenderSupplier);
    }

    private Optional<ITokenSender> getTokenSender()
    {
        return new Reflections(getClass().getPackage().getName())
                .getSubTypesOf(ITokenSender.class)
                .stream()
                .filter(type -> type.getSimpleName().equals(configuration.getTokenSender()))
                .findAny()
                .map(this::newInstance);
    }

    private ITokenSender newInstance(Class<? extends ITokenSender> type)
    {
        try
        {
            return type.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            validationModel.addValidationError("Could not create token sender instance: " + e.getMessage());
            return new ConsoleTokenSender();
        }
    }
}
