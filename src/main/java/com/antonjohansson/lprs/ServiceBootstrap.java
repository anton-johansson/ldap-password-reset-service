package com.antonjohansson.lprs;

import javax.servlet.annotation.WebListener;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * Context listener that initializes Google Guice IOC.
 */
@WebListener
public class ServiceBootstrap extends GuiceServletContextListener
{
    @Override
    protected Injector getInjector()
    {
        return Guice.createInjector(new ServiceModule());
    }
}
