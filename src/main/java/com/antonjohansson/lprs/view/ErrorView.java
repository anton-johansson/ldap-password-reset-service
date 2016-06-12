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
