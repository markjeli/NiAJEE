package com.jelinski.niajee.user.controller.rest;

import com.jelinski.niajee.component.DtoFunctionFactory;
import com.jelinski.niajee.user.controller.api.UserController;
import com.jelinski.niajee.user.dto.GetUserResponse;
import com.jelinski.niajee.user.dto.GetUsersResponse;
import com.jelinski.niajee.user.dto.PutUserRequest;
import com.jelinski.niajee.user.service.UserService;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;

import java.io.InputStream;
import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
public class UserRestController implements UserController {

    /**
     * User service.
     */
    private final UserService service;

    /**
     * Factory producing functions for conversion between DTO and entities.
     */
    private final DtoFunctionFactory factory;

    /**
     * Allows to create {@link UriBuilder} based on current request.
     */
    private final UriInfo uriInfo;

    /**
     * Current HTTP Servlet response.
     */
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        //ATM in this implementation only HttpServletRequest can be injected with CDI so JAX-RS injection is used.
        this.response = response;
    }

    /**
     * @param service user service
     * @param factory factory producing functions for conversion between DTO and entities
     * @param uriInfo allows to create {@link UriBuilder} based on current request
     */
    @Inject
    public UserRestController(
            UserService service,
            DtoFunctionFactory factory,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo
    ) {
        this.service = service;
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @Override
    public GetUsersResponse getUsers() {
        return factory.usersToResponse().apply(service.findAll());
    }

    @Override
    public GetUserResponse getUser(UUID id) {
        return service.find(id)
                .map(factory.userToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void registerUser(UUID id, PutUserRequest request) {
        try {
            service.create(factory.requestToUser().apply(id, request));
            response.setHeader("Location", uriInfo.getAbsolutePathBuilder()
                    .path(UserController.class, "getUser")
                    .build(id)
                    .toString());
            throw  new WebApplicationException(Response.Status.CREATED);
        } catch (TransactionalException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
            throw ex;
        }

    }


    @Override
    public byte[] getUserPortrait(UUID id) {
        return service.getPortrait(id);
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
