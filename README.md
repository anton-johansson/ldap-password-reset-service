# LDAP Password Reset Service

[![Build Status](https://img.shields.io/travis/anton-johansson/ldap-password-reset-service/master.svg)](https://travis-ci.org/anton-johansson/ldap-password-reset-service)
[![License](https://img.shields.io/hexpm/l/plug.svg?maxAge=2592000)](https://raw.githubusercontent.com/anton-johansson/ldap-password-reset-service/master/LICENSE)

Provides a simple web service for resetting LDAP user passwords.


## Installing
```
wget https://github.com/anton-johansson/ldap-password-reset-service/releases/download/v1.0.0/ldap-password-reset-service_1.0.0_all.deb
dpkg --install ldap-password-reset-service_1.0.0_all.deb
```


## Development

#### Running in development mode
```mvn jetty:run -DconfigurationFile=/home/user/ldap-password-reset-service.conf```

#### Building a package
```mvn clean install package```

#### Running the package
```java -DconfigurationFile=/home/user/ldap-password-reset-service.conf -cp "target/ldap-password-reset-service-1.0.0-SNAPSHOT.jar:target/lib/*" com.antonjohansson.lprs.Application```


## Configuration
```
# LDAP settings
provider-url = ldaps://hostname:636
domain = domain.local
username = user
password = password

# Web service settings
#port = 8080

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
