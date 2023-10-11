package com.jelinski.niajee.motorcycleType.dto.function;

import com.jelinski.niajee.motorcycleType.dto.GetMotorcycleTypeResponse;
import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;

import java.util.function.Function;

/**
 * Function for converting {@link MotorcycleType} to {@link GetMotorcycleTypeResponse}.
 */
public class MotorcycleTypeToResponseFunction implements Function<MotorcycleType, GetMotorcycleTypeResponse> {

    @Override
    public GetMotorcycleTypeResponse apply(MotorcycleType motorcycleType) {
        return GetMotorcycleTypeResponse.builder()
                .id(motorcycleType.getId())
                .name(motorcycleType.getTypeName())
                .description(motorcycleType.getDescription())
                .ridingPosition(motorcycleType.getRidingPosition())
                .build();
    }

}
