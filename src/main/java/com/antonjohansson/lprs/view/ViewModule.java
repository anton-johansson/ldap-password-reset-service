package com.antonjohansson.lprs.view;

import com.google.inject.AbstractModule;

/**
 * Contains IOC bindings for the view module.
 */
public class ViewModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(IServicePresenter.class).to(ServicePresenter.class);
    }
}
