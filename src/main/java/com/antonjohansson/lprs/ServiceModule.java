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
package com.antonjohansson.lprs;

import static com.google.inject.Scopes.SINGLETON;

import com.antonjohansson.lprs.controller.ControllerModule;
import com.antonjohansson.lprs.view.ViewModule;
import com.google.inject.servlet.ServletModule;

/**
 * Provides IOC mappings.
 */
public class ServiceModule extends ServletModule
{
    @Override
    protected void configureServlets()
    {
        serve("/*").with(ServiceServlet.class);
        bind(ServiceServlet.class).in(SINGLETON);

        install(new ControllerModule());
        install(new ViewModule());
    }
}
