package com.jelinski.niajee.motorcycle.model.function;

import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.motorcycle.model.MotorcycleEditModel;
import com.jelinski.niajee.user.entity.User;
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
    public Motorcycle apply(Motorcycle entity, MotorcycleEditModel model) {
        return Motorcycle.builder()
                .id(entity.getId())
                .name(model.getName())
                .color(model.getColor())
                .price(model.getPrice())
                .horsepower(entity.getHorsepower())
                .brand(entity.getBrand())
                .productionDate(entity.getProductionDate())
                .weight(entity.getWeight())
                .motorcycleType(entity.getMotorcycleType())
                .user(User.builder()
                        .id(model.getUser().getId())
                        .build())
                .version(model.getVersion())
                .creationDateTime(entity.getCreationDateTime())
                .lastUpdateDateTime(entity.getLastUpdateDateTime())
                .build();
    }

}
