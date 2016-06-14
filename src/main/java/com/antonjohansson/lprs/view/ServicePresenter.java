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
package com.antonjohansson.lprs.view;

import static com.antonjohansson.lprs.view.ServiceView.Stage.REQUEST_TOKEN;
import static com.antonjohansson.lprs.view.ServiceView.Stage.SET_PASSWORD;
import static com.antonjohansson.lprs.view.ServiceView.Stage.USE_TOKEN;

import java.util.Optional;

import javax.inject.Inject;

import com.antonjohansson.lprs.controller.ILdapFactory;
import com.antonjohansson.lprs.controller.Ldap;
import com.antonjohansson.lprs.controller.token.ITokenSender;
import com.antonjohansson.lprs.controller.token.TokenGenerator;
import com.antonjohansson.lprs.controller.validation.IValidationModel;
import com.antonjohansson.lprs.model.User;
import com.vaadin.ui.Layout;

/**
 * Default implementation of {@link IServicePresenter}.
 */
class ServicePresenter implements IServicePresenter
{
    private final ServiceView view;
    private final ErrorView errorView;
    private final ILdapFactory ldapFactory;
    private final Feedback feedback;
    private final ITokenSender tokenSender;
    private final IValidationModel validationModel;
    private String token = "";
    private User user;

    @Inject
    ServicePresenter(
            ServiceView view,
            ErrorView errorView, ILdapFactory ldapFactory,
            Feedback feedback,
            ITokenSender tokenSender,
            IValidationModel validationModel)
    {
        this.view = view;
        this.errorView = errorView;
        this.ldapFactory = ldapFactory;
        this.feedback = feedback;
        this.tokenSender = tokenSender;
        this.validationModel = validationModel;
    }

    @Override
    public Layout getView()
    {
        if (validationModel.isValid())
        {
            return view;
        }
        else
        {
            return errorView;
        }
    }

    @Override
    public void initialize()
    {
        view.requestToken.addClickListener(e -> requestToken());
        view.backFromUseToken.addClickListener(e -> view.clear());
        view.backFromSetPassword.addClickListener(e -> view.clear());
        view.useToken.addClickListener(e -> useToken());
        view.setPassword.addClickListener(e -> setPassword());

        view.show(REQUEST_TOKEN);

        errorView.setValidationErrors(validationModel.getValidationErrors());
    }

    private void requestToken()
    {
        String username = view.username.getValue();
        if (username.isEmpty())
        {
            feedback.info("Username is required.");
            return;
        }

        try (Ldap ldap = ldapFactory.getLdap())
        {
            Optional<User> user = ldap.getUserByName(username);
            if (user.isPresent())
            {
                requestToken(user.get());
            }
            else
            {
                feedback.info("No user with the given user name was found.", view::clear);
            }
        }
    }

    private void requestToken(User user)
    {
        if (user.getTelephoneNumber().isEmpty())
        {
            feedback.info("Your user have no cellphone number.", view::clear);
            return;
        }

        this.user = user;
        this.token = TokenGenerator.generate();
        tokenSender.send(user, token);

        String greeting = "Greetings, " + user.getName() + "! Check your phone.";
        view.greeting.setValue(greeting);
        view.show(USE_TOKEN);
    }

    private void useToken()
    {
        String input = view.token.getValue();
        if (input.isEmpty())
        {
            feedback.info("Token is required.");
            return;
        }

        if (!input.equals(token))
        {
            feedback.info("Incorrect token.", view::clear);
            return;
        }

        view.show(SET_PASSWORD);
    }

    private void setPassword()
    {
        String password = view.newPassword.getValue();
        String passwordRepeat = view.newPasswordRepeat.getValue();

        if (password.isEmpty())
        {
            feedback.info("Password is required.", this::clearPasswordFields);
            return;
        }

        if (!password.equals(passwordRepeat))
        {
            feedback.info("Passwords must match.", this::clearPasswordFields);
            return;
        }

        try (Ldap ldap = ldapFactory.getLdap())
        {
            boolean success = ldap.setPassword(user.getDistinguishedName(), password);
            if (success)
            {
                feedback.info("Successfully updated password!", view::clear);
            }
            else
            {
                feedback.info("The password does not meet the requirements", this::clearPasswordFields);
            }
        }
    }

    private void clearPasswordFields()
    {
        view.newPassword.setValue("");
        view.newPasswordRepeat.setValue("");
    }
}
