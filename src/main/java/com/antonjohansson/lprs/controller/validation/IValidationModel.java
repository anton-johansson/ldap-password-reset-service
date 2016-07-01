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
