package com.antonjohansson.lprs;

import static java.util.EnumSet.allOf;
import static org.eclipse.jetty.servlet.ServletContextHandler.SESSIONS;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import com.google.inject.Guice;
import com.google.inject.servlet.GuiceFilter;

/**
 * Defines the entry point.
 */
public class Application
{
    private static final int PORT = 8080;

    /**
     * The application main entry-point.
     */
    public static void main(String[] args) throws Exception
    {
        try
        {
            Guice.createInjector(new ServiceModule());

            ServletContextHandler handler = new ServletContextHandler(SESSIONS);
            handler.setContextPath("/");
            handler.addFilter(GuiceFilter.class, "/*", allOf(DispatcherType.class));
            handler.addServlet(DefaultServlet.class, "/");

            Server server = new Server(PORT);
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
