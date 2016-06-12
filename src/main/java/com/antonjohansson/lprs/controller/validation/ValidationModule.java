package com.antonjohansson.lprs.controller.validation;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * Contains IOC bindings for the validation module.
 */
public class ValidationModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(IValidationModel.class).to(ValidationModel.class).in(Singleton.class);
    }
}
