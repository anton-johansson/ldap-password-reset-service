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

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.antonjohansson.lprs.model.User;

/**
 * Abstract skeleton for {@link ITokenSender} implementations that send e-mails.
 */
public abstract class AEmailTokenSender implements ITokenSender
{
    private static final Logger LOG = LoggerFactory.getLogger(AEmailTokenSender.class);

    private String host;

    public void setHost(String host)
    {
        this.host = host;
    }

    @Override
    public final void send(User user, String token)
    {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", host);

        Session session = Session.getDefaultInstance(properties);

        try
        {
            String from = getFrom();
            String to = getTo(user);

            LOG.info("Sending email from '{}' to '{}' using '{}'", from, to, host);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(getSubject());
            message.setContent(getBody(token), getContentType());

            Transport.send(message);
        }
        catch (MessagingException e)
        {
            LOG.error("Exception occurred when sending e-mail", e);
        }
    }

    /**
     * Gets the e-mail address to send from.
     *
     * @return Returns the 'From' address.
     */
    protected abstract String getFrom();

    /**
     * Gets the e-mail address to send to.
     *
     * @param user The user that requests the token.
     * @return Returns the 'To' address.
     */
    protected abstract String getTo(User user);

    /**
     * Gets the subject to send.
     *
     * @return Returns the subject.
     */
    protected abstract String getSubject();

    /**
     * Gets the body to send.
     *
     * @param token The token to send.
     * @return Returns the body.
     */
    protected abstract String getBody(String token);

    /**
     * Gets the content type of the body to send.
     *
     * @return Returns the content type.
     */
    protected abstract String getContentType();
}
