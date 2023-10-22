package com.jelinski.niajee.motorcycleType.model.function;

import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import com.jelinski.niajee.motorcycleType.model.MotorcycleTypesModel;

import java.util.List;
import java.util.function.Function;

/**
 * Converts {@link List<MotorcycleType>} to {@link MotorcycleTypesModel}.
 */
public class MotorcycleTypesToModelFunction implements Function<List<MotorcycleType>, MotorcycleTypesModel> {
    @Override
    public MotorcycleTypesModel apply(List<MotorcycleType> motorcycleTypes) {
        return MotorcycleTypesModel.builder()
                .motorcycleTypes(motorcycleTypes.stream()
                        .map(motorcycleType -> MotorcycleTypesModel.MotorcycleType.builder()
                                .id(motorcycleType.getId())
                                .typeName(motorcycleType.getTypeName())
                                .build())
                        .toList())
                .build();
    }
}
