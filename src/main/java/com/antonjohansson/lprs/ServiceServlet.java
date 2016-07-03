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

import javax.inject.Inject;
import javax.servlet.ServletException;

import org.slf4j.MDC;

import com.google.inject.Injector;
import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

/**
 * The main servlet.
 */
class ServiceServlet extends VaadinServlet
{
    private static final String MDC_REMOTE_ADDRESS = "remoteAddress";

    private final Injector injector;

    @Inject
    ServiceServlet(Injector injector)
    {
        this.injector = injector;
    }

    @Override
    protected void servletInitialized() throws ServletException
    {
        getService().addSessionInitListener(event ->
        {
            event.getSession().addUIProvider(new ServiceUiProvider());
        });
    }

    @Override
    protected VaadinServletService createServletService(DeploymentConfiguration deploymentConfiguration) throws ServiceException
    {
        VaadinServletService service = new VaadinServletService(this, deploymentConfiguration)
        {
            @Override
            public void requestStart(VaadinRequest request, VaadinResponse response)
            {
                String remoteAddress = request.getRemoteAddr();
                MDC.put(MDC_REMOTE_ADDRESS, remoteAddress);
                super.requestStart(request, response);
            }

            @Override
            public void requestEnd(VaadinRequest request, VaadinResponse response, VaadinSession session)
            {
                super.requestEnd(request, response, session);
                MDC.remove(MDC_REMOTE_ADDRESS);
            }
        };
        service.init();
        return service;
    }

    /**
     * Provides the main UI.
     */
    private class ServiceUiProvider extends UIProvider
    {
        @Override
        public Class<? extends UI> getUIClass(UIClassSelectionEvent event)
        {
            return ServiceUi.class;
        }

        @Override
        public UI createInstance(UICreateEvent event)
        {
            return injector.getInstance(ServiceUi.class);
        }
    }
}
