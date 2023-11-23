package com.jelinski.niajee.motorcycle.model.function;

import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.motorcycle.model.MotorcycleEditModel;
import com.jelinski.niajee.user.model.function.UserToModelFunction;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Converts {@link Motorcycle} to {@link MotorcycleEditModel}.
 */
public class MotorcycleToEditModelFunction implements Function<Motorcycle, MotorcycleEditModel>, Serializable {

    private final UserToModelFunction userToModelFunction;

    public MotorcycleToEditModelFunction(UserToModelFunction userToModelFunction) {
        this.userToModelFunction = userToModelFunction;
    }
    @Override
    public MotorcycleEditModel apply(Motorcycle motorcycle) {
        return MotorcycleEditModel.builder()
                .name(motorcycle.getName())
                .color(motorcycle.getColor())
                .price(motorcycle.getPrice())
                .user(userToModelFunction.apply(motorcycle.getUser()))
                .version(motorcycle.getVersion())
                .build();
    }
}
