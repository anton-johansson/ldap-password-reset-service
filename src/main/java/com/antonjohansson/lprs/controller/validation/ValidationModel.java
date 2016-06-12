package com.antonjohansson.lprs.controller.validation;

import static java.util.Collections.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Default implementation of {@link IValidationModel}.
 */
public class ValidationModel implements IValidationModel
{
    private final Set<String> validationErrors = new HashSet<>();

    @Override
    public void addValidationError(String message)
    {
        validationErrors.add(message);
    }

    @Override
    public Collection<String> getValidationErrors()
    {
        List<String> errors = new ArrayList<>(validationErrors);
        sort(errors);
        return errors;
    }
}
