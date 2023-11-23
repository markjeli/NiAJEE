package com.jelinski.niajee.motorcycle.model.function;

import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.motorcycle.model.MotorcyclesModel;

import java.util.List;
import java.util.function.Function;

/**
 * Converts {@link List <Motorcycle>} to {@link MotorcyclesModel}.
 */
public class MotorcyclesToModelFunction implements Function<List<Motorcycle>, MotorcyclesModel> {

    @Override
    public MotorcyclesModel apply(List<Motorcycle> motorcycles) {
        return MotorcyclesModel.builder()
                .motorcycles(motorcycles.stream()
                        .map(motorcycle -> MotorcyclesModel.Motorcycle.builder()
                                .id(motorcycle.getId())
                                .name(motorcycle.getName())
                                .price(motorcycle.getPrice())
                                .version(motorcycle.getVersion())
                                .creationDateTime(motorcycle.getCreationDateTime())
                                .lastUpdateDateTime(motorcycle.getLastUpdateDateTime())
                                .build())
                        .toList())
                .build();
    }

}
