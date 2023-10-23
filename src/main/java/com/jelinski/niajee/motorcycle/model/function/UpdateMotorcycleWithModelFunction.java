package com.jelinski.niajee.motorcycle.model.function;

import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.motorcycle.model.MotorcycleEditModel;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.function.BiFunction;

/**
 * Returns new instance of {@link Motorcycle} based on provided value and updated with values from
 * {@link MotorcycleEditModel}.
 */
public class UpdateMotorcycleWithModelFunction implements BiFunction<Motorcycle, MotorcycleEditModel, Motorcycle>, Serializable {

    @Override
    @SneakyThrows
    public Motorcycle apply(Motorcycle entity, MotorcycleEditModel request) {
        return Motorcycle.builder()
                .id(entity.getId())
                .name(request.getName())
                .color(request.getColor())
                .price(request.getPrice())
                .horsepower(entity.getHorsepower())
                .brand(entity.getBrand())
                .productionDate(entity.getProductionDate())
                .weight(entity.getWeight())
                .motorcycleType(entity.getMotorcycleType())
                .build();
    }

}
