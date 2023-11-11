package com.jelinski.niajee.user.service;

import com.jelinski.niajee.user.entity.User;
import com.jelinski.niajee.user.entity.UserRoles;
import com.jelinski.niajee.user.repository.api.UserRepository;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for all business actions regarding user entity.
 */
@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class UserService {

    /**
     * Repository for user entity.
     */
    private final UserRepository repository;

    /**
     * Hash mechanism used for storing users' passwords.
     */
    private final Pbkdf2PasswordHash passwordHash;

    /**
     * Service for managing user portraits.
     */
    private final UserPortraitService userPortraitService;

    /**
     * Security context
     */
    private final SecurityContext securityContext;

    /**
     * @param repository      repository for character entity
     * @param passwordHash    hash mechanism used for storing users' passwords
     * @param securityContext security context
     */
    @Inject
    public UserService(
            UserRepository repository,
            @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash,
            UserPortraitService userPortraitService,
            @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext
    ) {
        this.repository = repository;
        this.passwordHash = passwordHash;
        this.userPortraitService = userPortraitService;
        this.securityContext = securityContext;
    }

    /**
     * @param id user's id
     * @return container (can be empty) with user
     */
    @RolesAllowed(UserRoles.USER)
    public Optional<User> find(UUID id) {
        checkAdminRoleOrOwner(repository.find(id));
        return repository.find(id);
    }

    /**
     * Seeks for single user using login and password. Can be used in authentication module.
     *
     * @param login user's login
     * @return container (can be empty) with user
     */
    @RolesAllowed(UserRoles.ADMIN)
    public Optional<User> find(String login) {
        return repository.findByLogin(login);
    }

    /**
     * @return all available users
     */
    @RolesAllowed(UserRoles.ADMIN)
    public List<User> findAll() {
        return repository.findAll();
    }

    /**
     * Saves new user. Password is hashed using configured hash algorithm.
     *
     * @param user new user to be saved
     */
    @PermitAll
    public void create(User user) {
        user.setPassword(passwordHash.generate(user.getPassword().toCharArray()));
        user.setRoles(List.of(UserRoles.USER));
        repository.create(user);
    }

    /**
     * @param login    user's login
     * @param password user's password
     * @return true if provided login and password are correct
     */
    @PermitAll
    public boolean verify(String login, String password) {
        return find(login)
                .map(user -> passwordHash.verify(password.toCharArray(), user.getPassword()))
                .orElse(false);
    }

    /**
     * Get user's portrait
     *
     * @param id user's id
     * @return byte array containing user's portrait
     */
    @RolesAllowed(UserRoles.USER)
    public byte[] getPortrait(UUID id) {
        Optional<User> user = repository.find(id);
        checkAdminRoleOrOwner(user);
        if (user.isPresent()) {
            try {
                return userPortraitService.getPortrait(user.get());
            } catch (IOException e) {
                throw new NotFoundException(e);
            }
        } else {
            throw new NotFoundException("User not found");
        }
    }

    /**
     * Updates portrait of the user.
     *
     * @param id user's id
     * @param is input stream containing new portrait
     */
    @RolesAllowed(UserRoles.USER)
    public void updatePortrait(UUID id, InputStream is) {
        checkAdminRoleOrOwner(repository.find(id));
        repository.find(id).ifPresent(user -> {
            try {
                byte[] portrait = is.readAllBytes();
                userPortraitService.savePortrait(user, portrait);
                repository.update(user);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        });
    }

    /**
     * Deletes portrait of the user.
     *
     * @param id user's id
     */
    @RolesAllowed(UserRoles.USER)
    public void deletePortrait(UUID id) {
        checkAdminRoleOrOwner(repository.find(id));
        repository.find(id).ifPresent(user -> {
            try {
                userPortraitService.deletePortrait(user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            repository.update(user);
        });
    }

    /**
     * @param user user to be checked
     * @throws EJBAccessException when caller principal has no admin role and is not character's owner
     */
    private void checkAdminRoleOrOwner(Optional<User> user) throws EJBAccessException {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return;
        }
        if (securityContext.isCallerInRole(UserRoles.USER)
                && user.isPresent()
                && user.get().getLogin().equals(securityContext.getCallerPrincipal().getName())) {
            return;
        }
        throw new EJBAccessException("Caller not authorized.");
    }

}
