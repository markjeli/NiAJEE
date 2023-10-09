package com.jelinski.niajee.user.controller.simple;

import com.jelinski.niajee.component.DtoFunctionFactory;
import com.jelinski.niajee.controller.servlet.exception.NotFoundException;
import com.jelinski.niajee.user.controller.api.UserController;
import com.jelinski.niajee.user.dto.GetUserResponse;
import com.jelinski.niajee.user.dto.GetUsersResponse;
import com.jelinski.niajee.user.entity.User;
import com.jelinski.niajee.user.service.UserService;


import java.io.InputStream;
import java.util.UUID;

public class UserSimpleController implements UserController {

    /**
     * User service.
     */
    private final UserService service;

    /**
     * Factory producing functions for conversion between DTO and entities.
     */
    private final DtoFunctionFactory factory;

    /**
     * @param service user service
     */
    public UserSimpleController(UserService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetUsersResponse getUsers() {
        return factory.usersToResponse().apply(service.findAll());
    }

    @Override
    public GetUserResponse getUser(UUID uuid) {
        return service.find(uuid)
                .map(factory.userToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public byte[] getUserPortrait(UUID id) {
        return service.find(id)
                .map(User::getPortrait)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putUserPortrait(UUID id, InputStream portrait) {
        service.find(id).ifPresentOrElse(
                user -> service.updatePortrait(id, portrait),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteUserPortrait(UUID id) {
        service.find(id).ifPresentOrElse(
                user -> service.deletePortrait(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

}
