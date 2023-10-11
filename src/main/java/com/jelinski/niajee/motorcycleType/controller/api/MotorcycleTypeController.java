package com.jelinski.niajee.motorcycleType.controller.api;

import com.jelinski.niajee.motorcycleType.dto.GetMotorcycleTypeResponse;
import com.jelinski.niajee.motorcycleType.dto.GetMotorcycleTypesResponse;

import java.util.UUID;

/**
 * Controller for managing collections motorcycleTypes' representations.
 */
public interface MotorcycleTypeController {

    /**
     * @return all motorcycleTypes representation
     */
    GetMotorcycleTypesResponse getMotorcycleTypes();

    /**
     * @param id motorcycleType's id
     * @return motorcycleType representation
     */
    GetMotorcycleTypeResponse getMotorcycleType(UUID id);
}
