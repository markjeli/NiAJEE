package com.jelinski.niajee.motorcycle.model.function;

import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.motorcycle.model.MotorcycleModel;

import java.util.function.Function;

/**
 * Function for converting {@link Motorcycle} to {@link MotorcycleModel}.
 */
public class MotorcycleToModelFunction implements Function<Motorcycle, MotorcycleModel> {

    @Override
    public MotorcycleModel apply(Motorcycle motorcycle) {
        return MotorcycleModel.builder()
                .name(motorcycle.getName())
                .price(motorcycle.getPrice())
                .horsepower(motorcycle.getHorsepower())
                .color(motorcycle.getColor())
                .brand(motorcycle.getBrand())
                .productionDate(motorcycle.getProductionDate())
                .weight(motorcycle.getWeight())
                .type(motorcycle.getMotorcycleType().getTypeName())
                .build();
    }

}
