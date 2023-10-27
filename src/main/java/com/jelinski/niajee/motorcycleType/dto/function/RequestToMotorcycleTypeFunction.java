package com.jelinski.niajee.motorcycleType.dto.function;

import com.jelinski.niajee.motorcycleType.dto.PutMotorcycleTypeRequest;
import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;

import java.util.UUID;
import java.util.function.BiFunction;

/**
 * Converts {@link PutMotorcycleTypeRequest} to {@link MotorcycleType}. Caution, some fields are not set as they should be updated
 * by business logic.
 */
public class RequestToMotorcycleTypeFunction implements BiFunction<UUID, PutMotorcycleTypeRequest, MotorcycleType> {
    @Override
    public MotorcycleType apply(UUID uuid, PutMotorcycleTypeRequest putMotorcycleTypeRequest) {
        return MotorcycleType.builder()
                .id(uuid)
                .typeName(putMotorcycleTypeRequest.getTypeName())
                .description(putMotorcycleTypeRequest.getDescription())
                .ridingPosition(putMotorcycleTypeRequest.getRidingPosition())
                .build();
    }
}
