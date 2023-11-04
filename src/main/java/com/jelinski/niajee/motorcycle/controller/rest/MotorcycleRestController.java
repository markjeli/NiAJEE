package com.jelinski.niajee.motorcycle.controller.rest;

import com.jelinski.niajee.component.DtoFunctionFactory;
import com.jelinski.niajee.motorcycle.controller.api.MotorcycleController;
import com.jelinski.niajee.motorcycle.dto.GetMotorcycleResponse;
import com.jelinski.niajee.motorcycle.dto.GetMotorcyclesResponse;
import com.jelinski.niajee.motorcycle.dto.PatchMotorcycleRequest;
import com.jelinski.niajee.motorcycle.dto.PutMotorcycleRequest;
import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.motorcycle.service.MotorcycleService;
import com.jelinski.niajee.motorcycleType.service.MotorcycleTypeService;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;

import java.util.UUID;

/**
 * Simple framework agnostic implementation of controller.
 */
@Path("")
public class MotorcycleRestController implements MotorcycleController {

    /**
     * Service layer for all business actions regarding motorcycle entity.
     */
    private final MotorcycleService motorcycleService;

    /**
     * Service layer for all business actions regarding motorcycleType entity.
     */
    private final MotorcycleTypeService motorcycleTypeService;

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
     * @param motorcycleService character service
     * @param factory factory producing functions for conversion between DTO and entities
     * @param uriInfo allows to create {@link UriBuilder} based on current request
     */
    @Inject
    public MotorcycleRestController(
            MotorcycleService motorcycleService,
            MotorcycleTypeService motorcycleTypeService,
            DtoFunctionFactory factory,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo
    ) {
        this.motorcycleService = motorcycleService;
        this.motorcycleTypeService = motorcycleTypeService;
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @Override
    public GetMotorcyclesResponse getMotorcycles() {
        return factory.motorcyclesToResponse().apply(motorcycleService.findAll());
    }

    @Override
    public GetMotorcyclesResponse getMotorcycleTypeMotorcycles(UUID id) {
        return motorcycleService.findAllByMotorcycleType(id)
                .map(factory.motorcyclesToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetMotorcycleResponse getMotorcycle(UUID id) {
        return motorcycleService.find(id)
                .map(factory.motorcycleToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetMotorcycleResponse getMotorcycleTypeMotorcycle(UUID typeId, UUID id) {
        try {
            motorcycleTypeService.find(typeId).orElseThrow(NotFoundException::new);
            return motorcycleService.find(id)
                    .map(factory.motorcycleToResponse())
                    .orElseThrow(NotFoundException::new);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    @SneakyThrows
    public void putMotorcycle(UUID typeId, UUID id, PutMotorcycleRequest request) {
        try {
            Motorcycle motorcycle = factory.requestToMotorcycle().apply(id, request);
            motorcycle.setMotorcycleType(motorcycleTypeService.find(typeId).orElseThrow(NotFoundException::new));
            motorcycleService.create(motorcycle);
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(MotorcycleController.class, "getMotorcycle")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void patchMotorcycle(UUID typeId, UUID id, PatchMotorcycleRequest request) {
        motorcycleTypeService.find(typeId).orElseThrow(NotFoundException::new);
        motorcycleService.find(id).ifPresentOrElse(
                motorcycle -> motorcycleService.update(factory.updateMotorcycle().apply(motorcycle, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteMotorcycle(UUID id) {
        motorcycleService.find(id).ifPresentOrElse(
                motorcycle -> motorcycleService.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

}
