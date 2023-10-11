package com.jelinski.niajee.motorcycleType.dto.function;

import com.jelinski.niajee.motorcycleType.dto.GetMotorcycleTypesResponse;
import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;

import java.util.List;
import java.util.function.Function;

/**
 * Function for converting {@link MotorcycleType} to {@link GetMotorcycleTypesResponse}.
 */
public class MotorcycleTypesToResponseFunction implements Function<List<MotorcycleType>, GetMotorcycleTypesResponse> {

    @Override
    public GetMotorcycleTypesResponse apply(List<MotorcycleType> motorcycleTypes) {
        return GetMotorcycleTypesResponse.builder()
                .motorcycleTypes(motorcycleTypes.stream()
                        .map(motorcycleType -> GetMotorcycleTypesResponse.MotorcycleType.builder()
                                .id(motorcycleType.getId())
                                .name(motorcycleType.getTypeName())
                                .build())
                        .toList())
                .build();
    }

}
