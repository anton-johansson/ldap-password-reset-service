package com.antonjohansson.lprs.view;

import de.steinwedel.messagebox.MessageBox;

/**
 * Provides operations for showing feedback.
 */
class Feedback
{
    private static final Runnable DO_NOTHING = () ->
    {
    };

    /**
     * Shows an informational feedback.
     *
     * @param message The message to show.
     */
    void info(String message)
    {
        info(message, DO_NOTHING);
    }

    /**
     * Shows an informational feedback.
     *
     * @param message The message to show.
     * @param onOkay Action to execute when the 'OK' button is clicked.
     */
    void info(String message, Runnable onOkay)
    {
        MessageBox
                .createInfo()
                .withMessage(message)
                .withOkButton(onOkay)
                .open();
    }
}
