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
