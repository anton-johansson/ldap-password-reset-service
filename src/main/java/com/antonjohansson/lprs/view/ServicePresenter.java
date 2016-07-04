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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.antonjohansson.lprs.controller.ILdapFactory;
import com.antonjohansson.lprs.controller.Ldap;
import com.antonjohansson.lprs.controller.configuration.Configuration;
import com.antonjohansson.lprs.controller.spam.ISpamController;
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
    private static final Logger LOG = LoggerFactory.getLogger(ServicePresenter.class);

    private final ServiceView view;
    private final ErrorView errorView;
    private final ILdapFactory ldapFactory;
    private final Feedback feedback;
    private final ITokenSender tokenSender;
    private final ISpamController spamController;
    private final Configuration configuration;
    private final IValidationModel validationModel;
    private String token = "";
    private User user;

    @Inject
    ServicePresenter(
            ServiceView view,
            ErrorView errorView,
            ILdapFactory ldapFactory,
            Feedback feedback,
            ITokenSender tokenSender,
            ISpamController spamController,
            Configuration configuration,
            IValidationModel validationModel)
    {
        this.view = view;
        this.errorView = errorView;
        this.ldapFactory = ldapFactory;
        this.feedback = feedback;
        this.tokenSender = tokenSender;
        this.spamController = spamController;
        this.configuration = configuration;
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
        LOG.debug("Initializing presenter");

        view.username.addValueChangeListener(event -> enableRequestTokenButton());
        view.captcha.setVisible(configuration.getBoolean("recaptcha.enabled"));
        view.captcha.setSiteKey(configuration.getOrDefault("recaptcha.site-key", ""));
        view.captcha.setSecretKey(configuration.getOrDefault("recaptcha.secret-key", ""));
        view.captcha.addCheckPassedListener(this::checkPassedHandler);
        view.captcha.addPassedCheckExpiredListener(this::passedCheckExpiredHandler);
        view.requestToken.addClickListener(e -> requestToken());
        view.backFromUseToken.addClickListener(e -> view.clear());
        view.backFromSetPassword.addClickListener(e -> view.clear());
        view.useToken.addClickListener(e -> useToken());
        view.setPassword.addClickListener(e -> setPassword());

        view.show(REQUEST_TOKEN);

        errorView.setValidationErrors(validationModel.getValidationErrors());

        LOG.debug("Initialized presenter");
    }

    private boolean isRecaptchaVerified()
    {
        boolean recaptchaEnabled = configuration.getBoolean("recaptcha.enabled");
        boolean recaptchaVerified = view.captcha.isVerified();

        return recaptchaVerified
            || !recaptchaEnabled;
    }

    private void checkPassedHandler()
    {
        LOG.debug("Captcha passed");
        enableRequestTokenButton();
    }

    private void passedCheckExpiredHandler()
    {
        LOG.debug("Captcha pass expired");
        enableRequestTokenButton();
    }

    private void enableRequestTokenButton()
    {
        boolean recaptchaVerified = isRecaptchaVerified();
        boolean hasUsernameText = !view.username.getValue().isEmpty();

        view.requestToken.setEnabled(recaptchaVerified && hasUsernameText);
    }

    private void requestToken()
    {
        boolean verified = isRecaptchaVerified();
        if (!verified)
        {
            LOG.warn("Captcha was not verified");
            feedback.info("Verify yourself using the captcha.", view::clear);
            return;
        }

        String username = view.username.getValue();
        if (username.isEmpty())
        {
            feedback.info("Username is required.", view::clear);
            return;
        }

        try (Ldap ldap = ldapFactory.getLdap())
        {
            Optional<User> user = ldap.getUserByName(username);
            if (user.isPresent())
            {
                LOG.debug("Requesting token for user '{}'", user.get().getUserPrincipalName());
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
        if (!spamController.check(user.getUserPrincipalName()))
        {
            LOG.warn("Request for '{}' was denied due to too many requests", user.getUserPrincipalName());
            feedback.info("You have requested a token too many times, please wait a while.", view::clear);
            return;
        }

        if (user.getTelephoneNumber().isEmpty())
        {
            LOG.warn("User '{}' has no telephone number", user.getUserPrincipalName());
            feedback.info("Your user have no cellphone number.", view::clear);
            return;
        }

        this.user = user;
        this.token = TokenGenerator.generate();
        LOG.info("Sending token '{}' to '{}'", token, user.getUserPrincipalName());
        tokenSender.send(user, token);

        String greeting = "Greetings, " + user.getName() + "! " + tokenSender.getSuccessMessage();
        view.greeting.setValue(greeting.trim());
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
                LOG.info("Successfully set new password for user '{}'", user.getUserPrincipalName());
                feedback.info("Successfully updated password!", view::clear);
            }
            else
            {
                LOG.info("Requested password for user '{}' did not meet the requirements", user.getUserPrincipalName());
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
