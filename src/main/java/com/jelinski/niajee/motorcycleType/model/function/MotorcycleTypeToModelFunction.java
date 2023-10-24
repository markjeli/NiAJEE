package com.jelinski.niajee.motorcycleType.model.function;

import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import com.jelinski.niajee.motorcycleType.model.MotorcycleTypeModel;

import java.util.function.Function;

/**
 * Function for converting {@link MotorcycleType} to {@link MotorcycleTypeModel}.
 */
public class MotorcycleTypeToModelFunction implements Function<MotorcycleType, MotorcycleTypeModel> {
    @Override
    public MotorcycleTypeModel apply(MotorcycleType motorcycleType) {
        return MotorcycleTypeModel.builder()
                .id(motorcycleType.getId())
                .typeName(motorcycleType.getTypeName())
                .build();
    }
}
