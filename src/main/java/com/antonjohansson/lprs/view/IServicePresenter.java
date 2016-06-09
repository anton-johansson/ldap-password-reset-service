package com.antonjohansson.lprs.view;

import com.vaadin.ui.Layout;

/**
 * Defines the main presenter.
 */
public interface IServicePresenter
{
    /**
     * Gets the view to present.
     *
     * @return Returns the view.
     */
    Layout getView();

    /**
     * Initializes the presenter.
     */
    void initialize();
}
