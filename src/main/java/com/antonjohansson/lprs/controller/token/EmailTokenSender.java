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
 * {@link ITokenSender} implementation that sends the token in an e-mail.
 */
public class EmailTokenSender extends AEmailTokenSender
{
    private String from;

    public void setFrom(String from)
    {
        this.from = from;
    }

    @Override
    protected String getFrom()
    {
        return from;
    }

    @Override
    protected String getTo(User user)
    {
        return user.getMail();
    }

    @Override
    protected String getSubject()
    {
        return "Password Reset";
    }

    @Override
    protected String getBody(String token)
    {
        return "Your token is '" + token + "'";
    }

    @Override
    protected String getContentType()
    {
        return "text/plain";
    }
}
