# LDAP Password Reset Service

Provides a simple web service for resetting LDAP user passwords.

[![Build Status](https://img.shields.io/travis/anton-johansson/ldap-password-reset-service/master.svg?style=flat-square)](https://travis-ci.org/anton-johansson/ldap-password-reset-service)
[![License](https://img.shields.io/github/license/anton-johansson/ldap-password-reset-service.svg?style=flat-square)](../master/LICENSE)

## Development

#### Running in development mode
```mvn jetty:run -DconfigurationFile=/home/user/ldap-password-reset-service.properties```

#### Building a package
```mvn clean install package```

#### Running the package
```java -jar -DconfigurationFile=/etc/ldap-password-reset-service/configuration.properties target/ldap-password-reset-service-1.0-SNAPSHOT-jar-with-dependencies.jar```

#### Configuration
```
# LDAP settings
provider-url = ldaps://hostname:636
domain = domain.local
username = user
password = password

# How to send access tokens?
token-sender = EmailTokenSender
token-sender.host = hostname-to-smpt-server
token-sender.from = sender@hostname.com

# Spam protection
#spam.request-count = 2
#spam.expire-time = 30

# reCAPTCHA protection
recaptcha.enabled = true
recaptcha.secret-key = my-secret-recaptcha-key
recaptcha.site-key = my-public-recaptcha-key
```

## License

Apache License © [Anton Johansson](https://github.com/anton-johansson)