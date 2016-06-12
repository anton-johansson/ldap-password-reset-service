package com.antonjohansson.lprs.controller.validation;

import java.util.Collection;

/**
 * Contains possible validation errors.
 */
public interface IValidationModel
{
    /**
     * Adds a validation error to the model.
     *
     * @param message The message to add.
     */
    void addValidationError(String message);

    /**
     * Gets all the validation errors.
     *
     * @return Returns the validation errors.
     */
    Collection<String> getValidationErrors();

    /**
     * Gets whether or not the validation model is valid.
     * 
     * @return Returns {@code true} if the model is valid.
     */
    default boolean isValid()
    {
        return getValidationErrors().isEmpty();
    }
}
