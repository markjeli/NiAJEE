package com.jelinski.niajee.user.service;

import com.jelinski.niajee.controller.servlet.exception.NotFoundException;
import com.jelinski.niajee.crypto.component.Pbkdf2PasswordHash;
import com.jelinski.niajee.user.entity.User;
import com.jelinski.niajee.user.repository.api.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for all business actions regarding user entity.
 */
@ApplicationScoped
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
     * @param repository   repository for character entity
     * @param passwordHash hash mechanism used for storing users' passwords
     */
    @Inject
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
     * Get user's portrait
     *
     * @param id user's id
     * @return byte array containing user's portrait
     */
    public byte[] getPortrait(UUID id) {
        Optional<User> user = repository.find(id);
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
    public void updatePortrait(UUID id, InputStream is) {
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
    public void deletePortrait(UUID id) {
        repository.find(id).ifPresent(user -> {
            try {
                userPortraitService.deletePortrait(user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            repository.update(user);
        });
    }

}
