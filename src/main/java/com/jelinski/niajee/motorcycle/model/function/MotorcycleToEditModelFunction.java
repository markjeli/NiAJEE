package com.jelinski.niajee.motorcycle.model.function;

import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.motorcycle.model.MotorcycleEditModel;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Converts {@link Motorcycle} to {@link MotorcycleEditModel}.
 */
public class MotorcycleToEditModelFunction implements Function<Motorcycle, MotorcycleEditModel>, Serializable {
    @Override
    public MotorcycleEditModel apply(Motorcycle motorcycle) {
        return MotorcycleEditModel.builder()
                .name(motorcycle.getName())
                .color(motorcycle.getColor())
                .price(motorcycle.getPrice())
                .build();
    }
}
