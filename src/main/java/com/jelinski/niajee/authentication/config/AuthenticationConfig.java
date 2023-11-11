package com.jelinski.niajee.authentication.config;

import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;

@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "NiAJEE")
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/MotorcycleCatalog",
        callerQuery = "select password from users where login = ?",
        groupsQuery = "select role from users__roles where id = (select id from users where login = ?)",
        hashAlgorithm = Pbkdf2PasswordHash.class
)
public class AuthenticationConfig {
}
