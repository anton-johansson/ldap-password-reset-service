package com.antonjohansson.lprs;

import javax.servlet.annotation.WebFilter;

import com.google.inject.servlet.GuiceFilter;

/**
 * Servlet filter that initializes Google Guice IOC.
 */
@WebFilter("/*")
public class ServiceFilter extends GuiceFilter
{
}
