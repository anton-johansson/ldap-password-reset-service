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
