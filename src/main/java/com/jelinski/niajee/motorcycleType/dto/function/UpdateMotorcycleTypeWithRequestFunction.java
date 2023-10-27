package com.jelinski.niajee.motorcycleType.dto.function;

import com.jelinski.niajee.motorcycleType.dto.PatchMotorcycleTypeRequest;
import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;

import java.util.function.BiFunction;

/**
 * Returns new instance of {@link MotorcycleType} based on provided value and updated with values from
 * {@link PatchMotorcycleTypeRequest}.
 */
public class UpdateMotorcycleTypeWithRequestFunction implements BiFunction<MotorcycleType, PatchMotorcycleTypeRequest, MotorcycleType> {
    @Override
    public MotorcycleType apply(MotorcycleType entity, PatchMotorcycleTypeRequest request) {
        return MotorcycleType.builder()
                .id(entity.getId())
                .typeName(request.getTypeName())
                .description(request.getDescription())
                .ridingPosition(entity.getRidingPosition())
                .motorcycles(entity.getMotorcycles())
                .build();
    }
}
