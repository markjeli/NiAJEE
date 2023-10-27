package com.jelinski.niajee.motorcycleType.controller.api;

import com.jelinski.niajee.motorcycleType.dto.GetMotorcycleTypeResponse;
import com.jelinski.niajee.motorcycleType.dto.GetMotorcycleTypesResponse;
import com.jelinski.niajee.motorcycleType.dto.PatchMotorcycleTypeRequest;
import com.jelinski.niajee.motorcycleType.dto.PutMotorcycleTypeRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

/**
 * Controller for managing collections motorcycleTypes' representations.
 */
@Path("")
public interface MotorcycleTypeController {

    /**
     * @return all motorcycleTypes representation
     */
    @GET
    @Path("/motorcycleTypes")
    @Produces(MediaType.APPLICATION_JSON)
    GetMotorcycleTypesResponse getMotorcycleTypes();

    /**
     * @param id motorcycleType's id
     * @return motorcycleType representation
     */
    @GET
    @Path("/motorcycleTypes/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetMotorcycleTypeResponse getMotorcycleType(@PathParam("id") UUID id);

    /**
     * @param id motorcycleType's id
     */
    @DELETE
    @Path("/motorcycleTypes/{id}")
    void deleteMotorcycleType(@PathParam("id") UUID id);

    /**
     * @param id motorcycleType's id
     * @param request new motorcycle type representation
     */
    @PUT
    @Path("/motorcycleTypes/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void putMotorcycleType(@PathParam("id") UUID id, PutMotorcycleTypeRequest request);

    /**
     * @param id motorcycleType's id
     * @param request motorcycle type update representation
     */
    @PATCH
    @Path("/motorcycleTypes/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchMotorcycleType(@PathParam("id") UUID id, PatchMotorcycleTypeRequest request);

}
