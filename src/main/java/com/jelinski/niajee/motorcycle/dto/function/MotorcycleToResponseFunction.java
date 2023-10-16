package com.jelinski.niajee.motorcycle.dto.function;

import com.jelinski.niajee.motorcycle.dto.GetMotorcycleResponse;
import com.jelinski.niajee.motorcycle.entity.Motorcycle;

import java.util.function.Function;

/**
 * Function for converting {@link Motorcycle} to {@link GetMotorcycleResponse}.
 */
public class MotorcycleToResponseFunction implements Function<Motorcycle, GetMotorcycleResponse> {

    @Override
    public GetMotorcycleResponse apply(Motorcycle entity) {
        return GetMotorcycleResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .horsepower(entity.getHorsepower())
                .color(entity.getColor())
                .brand(entity.getBrand())
                .productionDate(entity.getProductionDate())
                .price(entity.getPrice())
                .weight(entity.getWeight())
                .motorcycleType(GetMotorcycleResponse.MotorcycleType.builder()
                        .id(entity.getMotorcycleType().getId())
                        .typeName(entity.getMotorcycleType().getTypeName())
                        .build())
                .build();
    }

}
