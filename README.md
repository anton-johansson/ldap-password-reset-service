# ldap-password-reset-service

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
