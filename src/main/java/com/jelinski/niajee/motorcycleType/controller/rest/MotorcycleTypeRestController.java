package com.jelinski.niajee.motorcycleType.controller.rest;

import com.jelinski.niajee.component.DtoFunctionFactory;
import com.jelinski.niajee.motorcycleType.controller.api.MotorcycleTypeController;
import com.jelinski.niajee.motorcycleType.dto.GetMotorcycleTypeResponse;
import com.jelinski.niajee.motorcycleType.dto.GetMotorcycleTypesResponse;
import com.jelinski.niajee.motorcycleType.dto.PatchMotorcycleTypeRequest;
import com.jelinski.niajee.motorcycleType.dto.PutMotorcycleTypeRequest;
import com.jelinski.niajee.motorcycleType.service.MotorcycleTypeService;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.EJBException;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.util.UUID;
import java.util.logging.Level;

/**
 * Simple framework agnostic implementation of controller.
 */
@Path("")
@Log
public class MotorcycleTypeRestController implements MotorcycleTypeController {

    /**
     * Service for motorcycleType entity.
     */
    private MotorcycleTypeService service;

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
     * @param factory factory producing functions for conversion between DTO and entities
     * @param uriInfo allows to create {@link UriBuilder} based on current request
     */
    @Inject
    public MotorcycleTypeRestController(
            DtoFunctionFactory factory,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo
    ) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setService(MotorcycleTypeService service) {
        this.service = service;
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
        } catch (EJBException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            } else if (ex instanceof EJBAccessException) {
                throw new NotAuthorizedException(ex);
            }
            throw ex;
        }
    }

    @Override
    public void patchMotorcycleType(UUID id, PatchMotorcycleTypeRequest request) {
        try {
            service.find(id).ifPresentOrElse(

                    entity -> service.update(factory.updateMotorcycleType().apply(entity, request)),
                    () -> {
                        throw new NotFoundException();
                    }
            );
        } catch (EJBException ex) {
            throw new NotFoundException();
        }

    }

    @Override
    public void deleteMotorcycleType(UUID id) {
        try {
            service.find(id).ifPresentOrElse(
                    entity -> service.delete(id),
                    () -> {
                        throw new NotFoundException();
                    }
            );
        } catch (EJBAccessException ex) {
            throw new NotAuthorizedException(ex);
        }

    }

}
