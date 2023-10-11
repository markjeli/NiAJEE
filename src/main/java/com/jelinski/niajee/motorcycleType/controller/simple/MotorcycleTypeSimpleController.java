package com.jelinski.niajee.motorcycleType.controller.simple;

import com.jelinski.niajee.component.DtoFunctionFactory;
import com.jelinski.niajee.controller.servlet.exception.NotFoundException;
import com.jelinski.niajee.motorcycleType.controller.api.MotorcycleTypeController;
import com.jelinski.niajee.motorcycleType.dto.GetMotorcycleTypeResponse;
import com.jelinski.niajee.motorcycleType.dto.GetMotorcycleTypesResponse;
import com.jelinski.niajee.motorcycleType.service.MotorcycleTypeService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@RequestScoped
public class MotorcycleTypeSimpleController implements MotorcycleTypeController {

    /**
     * Service for motorcycleType entity.
     */
    private final MotorcycleTypeService service;

    /**
     * Factory producing functions for conversion between DTO and entities.
     */
    private final DtoFunctionFactory factory;

    /**
     * @param service service for motorcycleType entity
     * @param factory factory producing functions for conversion between DTO and entities
     */
    @Inject
    public MotorcycleTypeSimpleController(MotorcycleTypeService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
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

}
