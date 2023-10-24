package com.jelinski.niajee.motorcycle.model.function;

import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.motorcycle.model.MotorcycleCreateModel;
import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.function.Function;

public class ModelToMotorcycleFunction implements Function<MotorcycleCreateModel, Motorcycle>, Serializable {
    @Override
    @SneakyThrows
    public Motorcycle apply(MotorcycleCreateModel motorcycleCreateModel) {
        return Motorcycle.builder()
                .id(motorcycleCreateModel.getId())
                .name(motorcycleCreateModel.getName())
                .color(motorcycleCreateModel.getColor())
                .brand(motorcycleCreateModel.getBrand())
                .horsepower(motorcycleCreateModel.getHorsepower())
                .productionDate(motorcycleCreateModel.getProductionDate())
                .weight(motorcycleCreateModel.getWeight())
                .price(motorcycleCreateModel.getPrice())
                .motorcycleType(MotorcycleType.builder()
                        .id(motorcycleCreateModel.getMotorcycleType().getId())
                        .build())
                .build();
    }
}
