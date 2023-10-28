package com.jelinski.niajee.motorcycle.dto.function;

import com.jelinski.niajee.motorcycle.dto.PutMotorcycleRequest;
import com.jelinski.niajee.motorcycle.entity.Motorcycle;

import java.util.UUID;
import java.util.function.BiFunction;

/**
 * Converts {@link PutMotorcycleRequest} to {@link Motorcycle}.
 */
public class RequestToMotorcycleFunction implements BiFunction<UUID, PutMotorcycleRequest, Motorcycle> {

    @Override
    public Motorcycle apply(UUID uuid, PutMotorcycleRequest putMotorcycleRequest) {
        return Motorcycle.builder()
                .id(uuid)
                .name(putMotorcycleRequest.getName())
                .horsepower(putMotorcycleRequest.getHorsepower())
                .color(putMotorcycleRequest.getColor())
                .brand(putMotorcycleRequest.getBrand())
                .productionDate(putMotorcycleRequest.getProductionDate())
                .price(putMotorcycleRequest.getPrice())
                .weight(putMotorcycleRequest.getWeight())
                .build();
    }
}
