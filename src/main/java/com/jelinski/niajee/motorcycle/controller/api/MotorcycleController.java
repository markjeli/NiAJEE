package com.jelinski.niajee.motorcycle.controller.api;

import com.jelinski.niajee.motorcycle.dto.GetMotorcycleResponse;
import com.jelinski.niajee.motorcycle.dto.GetMotorcyclesResponse;
import com.jelinski.niajee.motorcycle.dto.PatchMotorcycleRequest;
import com.jelinski.niajee.motorcycle.dto.PutMotorcycleRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;


/**
 * Controller for managing collections motorcycles' representations.
 */
@Path("")
public interface MotorcycleController {

    /**
     * @return all motorcycles representation
     */
    @GET
    @Path("/motorcycles")
    @Produces(MediaType.APPLICATION_JSON)
    GetMotorcyclesResponse getMotorcycles();

    /**
     * @param id motorcycleType's id
     * @return motorcycles representation
     */
    @GET
    @Path("/motorcycleTypes/{id}/motorcycles")
    @Produces(MediaType.APPLICATION_JSON)
    GetMotorcyclesResponse getMotorcycleTypeMotorcycles(@PathParam("id") UUID id);

    /**
     * @param id motorcycle's id
     * @return motorcycle representation
     */
    @GET
    @Path("/motorcycles/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetMotorcycleResponse getMotorcycle(@PathParam("id") UUID id);

    /**
     * @param typeId motorcycleType's id
     * @param id motorcycle's id
     * @return motorcycles representation
     */
    @GET
    @Path("/motorcycleTypes/{typeId}/motorcycles/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetMotorcycleResponse getMotorcycleTypeMotorcycle(@PathParam("typeId") UUID typeId ,@PathParam("id") UUID id);

    /**
     * @param id      motorcycle's id
     * @param request new motorcycle representation
     */
    @PUT
    @Path("/motorcycleTypes/{typeId}/motorcycles/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void putMotorcycle(@PathParam("typeId") UUID typeId, @PathParam("id") UUID id, PutMotorcycleRequest request);

    /**
     * @param id      motorcycle's id
     * @param request motorcycle update representation
     */
    @PATCH
    @Path("/motorcycleTypes/{typeId}/motorcycles/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchMotorcycle(@PathParam("typeId") UUID typeId, @PathParam("id") UUID id, PatchMotorcycleRequest request);

    /**
     * @param id motorcycle's id
     */
    @DELETE
    @Path("/motorcycles/{id}")
    void deleteMotorcycle(@PathParam("id") UUID id);

}
