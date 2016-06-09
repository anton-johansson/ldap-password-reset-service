package com.antonjohansson.lprs;

import javax.inject.Inject;
import javax.servlet.ServletException;

import com.google.inject.Injector;
import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

/**
 * The main servlet.
 */
class ServiceServlet extends VaadinServlet
{
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
