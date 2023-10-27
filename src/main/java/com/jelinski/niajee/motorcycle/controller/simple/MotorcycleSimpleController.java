package com.jelinski.niajee.motorcycle.controller.simple;

import com.jelinski.niajee.component.DtoFunctionFactory;
import com.jelinski.niajee.motorcycle.controller.api.MotorcycleController;
import com.jelinski.niajee.motorcycle.dto.GetMotorcycleResponse;
import com.jelinski.niajee.motorcycle.dto.GetMotorcyclesResponse;
import com.jelinski.niajee.motorcycle.dto.function.PutMotorcycleRequest;
import com.jelinski.niajee.motorcycle.service.MotorcycleService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.UUID;

/**
 * Simple implementation of {@link MotorcycleController}.
 */
@RequestScoped
public class MotorcycleSimpleController implements MotorcycleController {  //TODO: Implement patch and put methods

    /**
     * Service layer for all business actions regarding motorcycle entity.
     */
    private final MotorcycleService service;

    /**
     * Factory producing functions for conversion between DTO and entities.
     */
    private final DtoFunctionFactory factory;

    /**
     * @param service character service
     * @param factory factory producing functions for conversion between DTO and entities
     */
    @Inject
    public MotorcycleSimpleController(MotorcycleService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetMotorcyclesResponse getMotorcycles() {
        return factory.motorcyclesToResponse().apply(service.findAll());
    }

    @Override
    public GetMotorcycleResponse getMotorcycle(UUID id) {
        return service.find(id)
                .map(factory.motorcycleToResponse())
                .orElseThrow(NotFoundException::new);
    }

//    @Override
//    public void PutMotorcycle(UUID id, PutMotorcycleRequest request) {
//        try {
//            service.create(factory.requestToMotorcycle().apply(id, request));
//        } catch (IllegalArgumentException e) {
//            throw new BadRequestException(e);
//        }
//    }

//    @Override
//    public void patchMotorcycle(UUID id, PutMotorcycleRequest request) {
//        service.find(id).ifPresentOrElse(
//                motorcycle -> service.update(factory.updateMotorcycle().apply(motorcycle, request)),
//                () -> {
//                    throw new NotFoundException();
//                }
//        );
//    }

    @Override
    public void deleteMotorcycle(UUID id) {
        service.find(id).ifPresentOrElse(
                motorcycle -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

}
