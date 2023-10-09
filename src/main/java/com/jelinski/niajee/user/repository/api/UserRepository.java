package com.jelinski.niajee.user.repository.api;

import com.jelinski.niajee.repository.api.Repository;
import com.jelinski.niajee.user.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends Repository<User, UUID> {

    /**
     * Seeks for single user using login.
     *
     * @param login user's login
     * @return container (can be empty) with user
     */
    Optional<User> findByLogin(String login);
}
