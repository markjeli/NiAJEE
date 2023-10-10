package com.jelinski.niajee.user.service;

import com.jelinski.niajee.crypto.component.Pbkdf2PasswordHash;
import com.jelinski.niajee.user.entity.User;
import com.jelinski.niajee.user.repository.api.UserRepository;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for all business actions regarding user entity.
 */
public class UserService {

    /**
     * Repository for user entity.
     */
    private final UserRepository repository;

    /**
     * Hash mechanism used for storing users' passwords.
     */
    private final Pbkdf2PasswordHash passwordHash;

    private final UserPortraitService userPortraitService;

    /**
     * @param repository   repository for character entity
     * @param passwordHash hash mechanism used for storing users' passwords
     */
    public UserService(UserRepository repository, Pbkdf2PasswordHash passwordHash, UserPortraitService userPortraitService) {
        this.repository = repository;
        this.passwordHash = passwordHash;
        this.userPortraitService = userPortraitService;
    }

    /**
     * @param id user's id
     * @return container (can be empty) with user
     */
    public Optional<User> find(UUID id) {
        return repository.find(id);
    }

    /**
     * Seeks for single user using login and password. Can be used in authentication module.
     *
     * @param login user's login
     * @return container (can be empty) with user
     */
    public Optional<User> find(String login) {
        return repository.findByLogin(login);
    }

    /**
     * @return all available users
     */
    public List<User> findAll() {
        return repository.findAll();
    }

    /**
     * Saves new user. Password is hashed using configured hash algorithm.
     *
     * @param user new user to be saved
     */
    public void create(User user) {
        user.setPassword(passwordHash.generate(user.getPassword().toCharArray()));
        repository.create(user);
    }

    /**
     * @param login    user's login
     * @param password user's password
     * @return true if provided login and password are correct
     */
    public boolean verify(String login, String password) {
        return find(login)
                .map(user -> passwordHash.verify(password.toCharArray(), user.getPassword()))
                .orElse(false);
    }

    /**
     * Updates portrait of the user.
     *
     * @param id user's id
     * @param is input stream containing new portrait
     */
    public void updatePortrait(UUID id, InputStream is) {
        repository.find(id).ifPresent(user -> {
            try {
                user.setPortrait(is.readAllBytes());
                userPortraitService.savePortrait(user);
                repository.update(user);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    /**
     * Deletes portrait of the user.
     * @param id user's id
     */
    public void deletePortrait(UUID id) {
        repository.find(id).ifPresent(user -> {
            user.setPortrait(null);
            userPortraitService.deletePortrait(id);
            repository.update(user);
        });
    }

}
