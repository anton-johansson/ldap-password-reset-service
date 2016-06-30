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

import com.antonjohansson.lprs.view.IServicePresenter;
import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;

/**
 * The main UI.
 */
@Theme("valo")
@JavaScript("https://www.google.com/recaptcha/api.js")
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
