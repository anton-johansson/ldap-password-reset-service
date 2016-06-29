# ldap-password-reset-service

Provides a simple web service for resetting LDAP user passwords.

[![Build Status](https://img.shields.io/travis/anton-johansson/ldap-password-reset-service/master.svg?style=flat-square)](https://travis-ci.org/anton-johansson/ldap-password-reset-service)
[![License](https://img.shields.io/github/license/anton-johansson/ldap-password-reset-service.svg?style=flat-square)](../master/LICENSE)

## Building
```mvn clean install package```

## Running
```java -jar -DconfigurationFile=/etc/ldap-password-reset-service/configuration.properties target/ldap-password-reset-service-1.0-SNAPSHOT-jar-with-dependencies.jar```

## Running in development
```mvn jetty:run -DconfigurationFile=/home/user/ldap-password-reset-service.properties```

## Configuration
```
providerURL=ldaps://hostname:636
domain=domain.local
username=user
password=password
```
