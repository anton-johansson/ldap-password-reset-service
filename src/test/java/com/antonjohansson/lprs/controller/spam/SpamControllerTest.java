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
package com.antonjohansson.lprs.controller.spam;

import static com.antonjohansson.lprs.controller.configuration.ConfigurationBuilder.configuration;
import static java.util.concurrent.TimeUnit.SECONDS;

import org.junit.Assert;
import org.junit.Test;

import com.antonjohansson.lprs.controller.configuration.Configuration;

/**
 * Unit tests of {@link SpamController}.
 */
public class SpamControllerTest extends Assert
{
    @Test
    public void test_that_too_many_requests_leads_to_deny()
    {
        Configuration configuration = configuration()
                .value("spam.request-count", "2")
                .value("spam.expire-time", "1")
                .build();

        ISpamController controller = new SpamController(configuration, SECONDS);

        assertCheck(controller, "anton-johansson", true);
        sleep(400);
        assertCheck(controller, "anton-johansson", true);
        sleep(400);
        assertCheck(controller, "anton-johansson", false);
        sleep(400);
        assertCheck(controller, "anton-johansson", true);
        sleep(400);
        assertCheck(controller, "anton-johansson", true);
        sleep(400);
        assertCheck(controller, "anton-johansson", false);
        sleep(400);
        assertCheck(controller, "anton-johansson", true);
    }

    @Test
    public void test_that_a_empty_request_count_always_accepts()
    {
        Configuration configuration = configuration()
                .value("spam.request-count", "0")
                .value("spam.expire-time", "1")
                .build();

        ISpamController controller = new SpamController(configuration, SECONDS);

        assertCheck(controller, "anton-johansson", true);
        assertCheck(controller, "anton-johansson", true);
        assertCheck(controller, "anton-johansson", true);
        assertCheck(controller, "anton-johansson", true);
        assertCheck(controller, "anton-johansson", true);
        assertCheck(controller, "anton-johansson", true);
        assertCheck(controller, "anton-johansson", true);
        assertCheck(controller, "anton-johansson", true);
    }

    @Test
    public void test_that_a_empty_expire_time_always_accepts()
    {
        Configuration configuration = configuration()
                .value("spam.request-count", "2")
                .value("spam.expire-time", "0")
                .build();

        ISpamController controller = new SpamController(configuration, SECONDS);

        assertCheck(controller, "anton-johansson", true);
        assertCheck(controller, "anton-johansson", true);
        assertCheck(controller, "anton-johansson", true);
        assertCheck(controller, "anton-johansson", true);
        assertCheck(controller, "anton-johansson", true);
        assertCheck(controller, "anton-johansson", true);
        assertCheck(controller, "anton-johansson", true);
        assertCheck(controller, "anton-johansson", true);
    }

    private void assertCheck(ISpamController controller, String user, boolean expected)
    {
        boolean actual = controller.check(user);
        assertEquals(expected, actual);
    }

    private void sleep(long milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }
}
