package com.jelinski.niajee.motorcycleType.controller.rest;

import com.jelinski.niajee.component.DtoFunctionFactory;
import com.jelinski.niajee.motorcycleType.controller.api.MotorcycleTypeController;
import com.jelinski.niajee.motorcycleType.dto.GetMotorcycleTypeResponse;
import com.jelinski.niajee.motorcycleType.dto.GetMotorcycleTypesResponse;
import com.jelinski.niajee.motorcycleType.dto.PatchMotorcycleTypeRequest;
import com.jelinski.niajee.motorcycleType.dto.PutMotorcycleTypeRequest;
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
public class MotorcycleTypeRestController implements MotorcycleTypeController {

    /**
     * Service for motorcycleType entity.
     */
    private final MotorcycleTypeService service;

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
     * @param service service for motorcycleType entity
     * @param factory factory producing functions for conversion between DTO and entities
     * @param uriInfo allows to create {@link UriBuilder} based on current request
     */
    @Inject
    public MotorcycleTypeRestController(
            MotorcycleTypeService service,
            DtoFunctionFactory factory,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo
    ) {
        this.service = service;
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @Override
    public GetMotorcycleTypesResponse getMotorcycleTypes() {
        return factory.motorcycleTypesToResponse().apply(service.findAll());
    }

    @Override
    public GetMotorcycleTypeResponse getMotorcycleType(UUID id) {
        return service.find(id)
                .map(factory.motorcycleTypeToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    @SneakyThrows
    public void putMotorcycleType(UUID id, PutMotorcycleTypeRequest request) {
        try {
            service.create(factory.requestToMotorcycleType().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(MotorcycleTypeController.class, "getMotorcycleType")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e);
        }
    }

    @Override
    public void patchMotorcycleType(UUID id, PatchMotorcycleTypeRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updateMotorcycleType().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteMotorcycleType(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

}
