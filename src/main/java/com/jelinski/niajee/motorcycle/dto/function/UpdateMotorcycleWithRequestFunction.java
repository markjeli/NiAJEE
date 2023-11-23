package com.jelinski.niajee.motorcycle.dto.function;

import com.jelinski.niajee.motorcycle.dto.PatchMotorcycleRequest;
import com.jelinski.niajee.motorcycle.entity.Motorcycle;

import java.util.function.BiFunction;

/**
 * Returns new instance of {@link Motorcycle} based on provided value and updated with values from
 * {@link PatchMotorcycleRequest}.
 */
public class UpdateMotorcycleWithRequestFunction implements BiFunction<Motorcycle, PatchMotorcycleRequest, Motorcycle> {

    @Override
    public Motorcycle apply(Motorcycle motorcycle, PatchMotorcycleRequest request) {
        return Motorcycle.builder()
                .id(motorcycle.getId())
                .name(request.getName())
                .price(request.getPrice())
                .color(request.getColor())
                .horsepower(motorcycle.getHorsepower())
                .brand(motorcycle.getBrand())
                .productionDate(motorcycle.getProductionDate())
                .weight(motorcycle.getWeight())
                .motorcycleType(motorcycle.getMotorcycleType())
                .user(motorcycle.getUser())
                .version(request.getVersion())
                .creationDateTime(motorcycle.getCreationDateTime())
                .lastUpdateDateTime(motorcycle.getLastUpdateDateTime())
                .build();
    }
}
