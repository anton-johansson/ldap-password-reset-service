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
