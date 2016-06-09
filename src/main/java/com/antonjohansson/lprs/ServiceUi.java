package com.antonjohansson.lprs;

import javax.inject.Inject;

import com.antonjohansson.lprs.view.IServicePresenter;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;

/**
 * The main UI.
 */
@Theme("valo")
class ServiceUi extends UI
{
    private final IServicePresenter presenter;

    @Inject
    ServiceUi(IServicePresenter presenter)
    {
        this.presenter = presenter;
    }

    @Override
    protected void init(VaadinRequest request)
    {
        presenter.initialize();

        Layout view = presenter.getView();
        setContent(view);
    }
}
