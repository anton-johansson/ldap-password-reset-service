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
package com.antonjohansson.lprs.view;

import static com.vaadin.ui.Alignment.MIDDLE_CENTER;

import java.util.Collection;

import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

/**
 * View for showing validation errors.
 */
class ErrorView extends VerticalLayout
{
    private final Layout layout = layout();

    ErrorView()
    {
        addComponent(layout);
        setSizeFull();
        setComponentAlignment(layout, MIDDLE_CENTER);
    }

    /**
     * Sets the validation errors.
     */
    public void setValidationErrors(Collection<String> validationErrors)
    {
        layout.removeAllComponents();
        validationErrors
                .stream()
                .map(error -> " - " + error)
                .map(Label::new)
                .forEach(layout::addComponent);
    }

    private VerticalLayout layout()
    {
        VerticalLayout layout = new VerticalLayout();
        layout.setCaption("Errors found when starting the application");
        layout.setSpacing(true);
        layout.setWidthUndefined();
        return layout;
    }
}
