package com.antonjohansson.lprs;

import static java.util.EnumSet.allOf;
import static org.eclipse.jetty.servlet.ServletContextHandler.SESSIONS;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import com.antonjohansson.lprs.controller.configuration.Configuration;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;

/**
 * Defines the entry point.
 */
public class Application
{
    /**
     * The application main entry-point.
     */
    public static void main(String[] args) throws Exception
    {
        try
        {
            Injector injector = Guice.createInjector(new ServiceModule());
            Configuration configuration = injector.getInstance(Configuration.class);

            ServletContextHandler handler = new ServletContextHandler(SESSIONS);
            handler.setContextPath("/");
            handler.addFilter(GuiceFilter.class, "/*", allOf(DispatcherType.class));
            handler.addServlet(DefaultServlet.class, "/");

            Server server = new Server(configuration.getPort());
            server.setHandler(handler);
            server.start();
            server.join();
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
}
