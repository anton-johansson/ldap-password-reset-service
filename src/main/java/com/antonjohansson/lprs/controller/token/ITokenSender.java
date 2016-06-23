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
package com.antonjohansson.lprs.controller.token;

import com.antonjohansson.lprs.model.User;

/**
 * Sends tokens to user.
 */
public interface ITokenSender
{
    /**
     * Sends a token to a user.
     *
     * @param user The user to send the token to.
     * @param token The token to send.
     */
    void send(User user, String token);

    /**
     * Gets the message that is shown when a token is successfully sent, for
     * example "Check your phone.".
     *
     * @return Returns the message to show upon success.
     */
    default String getSuccessMessage()
    {
        return "";
    }
}
