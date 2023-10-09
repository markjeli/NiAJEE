package com.jelinski.niajee.user.controller.api;

import com.jelinski.niajee.user.dto.GetUserResponse;
import com.jelinski.niajee.user.dto.GetUsersResponse;

import java.io.InputStream;
import java.util.UUID;

public interface UserController {

    /**
     * @return all users representation
     */
    GetUsersResponse getUsers();

    /**
     * @param uuid user's id
     * @return user representation
     */
    GetUserResponse getUser(UUID uuid);

    /**
     * @param id user's id
     * @return user's portrait
     */
    byte[] getUserPortrait(UUID id);

    /**
     * @param id user's id
     * @param portrait user's new avatar
     */
    void putUserPortrait(UUID id, InputStream portrait);

    /**
     * @param id user's id
     */
    void deleteUserPortrait(UUID id);

}
