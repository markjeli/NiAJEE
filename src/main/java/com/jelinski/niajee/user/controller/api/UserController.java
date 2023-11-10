package com.jelinski.niajee.user.controller.api;

import com.jelinski.niajee.user.dto.GetUserResponse;
import com.jelinski.niajee.user.dto.GetUsersResponse;
import com.jelinski.niajee.user.dto.PutUserRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.io.InputStream;
import java.util.UUID;

@Path("")
public interface UserController {

    /**
     * @return all users representation
     */
    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    GetUsersResponse getUsers();

    /**
     * @param id user's id
     * @return user representation
     */
    @GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetUserResponse getUser(@PathParam("id") UUID id);

    /**
     * @param id user's id
     */
    @PUT
    @Path("/users/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void registerUser(@PathParam("id") UUID id, PutUserRequest request);

    /**
     * @param id user's id
     * @return user's portrait
     */
    @GET
    @Path("/users/{id}/portrait")
    @Produces("image/png")
    byte[] getUserPortrait(@PathParam("id") UUID id);

    /**
     * @param id user's id
     * @param portrait user's new avatar
     */
    @PUT
    @Path("/users/{id}/portrait")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    void putUserPortrait(
            @PathParam("id") UUID id,
            @SuppressWarnings("RestParamTypeInspection")@FormParam("portrait") InputStream portrait
    );

    /**
     * @param id user's id
     */
    @DELETE
    @Path("/users/{id}/portrait")
    void deleteUserPortrait(@PathParam("id") UUID id);

}
