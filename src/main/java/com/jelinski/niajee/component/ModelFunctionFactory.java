package com.jelinski.niajee.component;

import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.motorcycle.model.function.MotorcycleToModelFunction;
import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import com.jelinski.niajee.motorcycle.model.function.MotorcyclesToModelFunction;
import com.jelinski.niajee.motorcycle.model.MotorcyclesModel;
import com.jelinski.niajee.motorcycleType.model.function.MotorcycleTypesToModelFunction;
import com.jelinski.niajee.motorcycleType.model.MotorcycleTypesModel;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.function.Function;

/**
 * Factor for creating {@link Function} implementation for converting between various objects used in different layers.
 * Instead of injecting multiple function objects single factory is injected.
 */
@ApplicationScoped
public class ModelFunctionFactory {

    /**
     * Returns a function to convert a list of {@link Motorcycle} to {@link MotorcyclesModel}.
     *
     * @return new instance
     */
    public MotorcyclesToModelFunction motorcyclesToModel() {
        return new MotorcyclesToModelFunction();
    }

    /**
     * Returns a function to convert a list of {@link MotorcycleType} to {@link MotorcycleTypesModel}.
     *
     * @return new instance
     */
    public MotorcycleTypesToModelFunction motorcycleTypesToModel() {
        return new MotorcycleTypesToModelFunction();
    }

    /**
     * Returns a function to convert a {@link Motorcycle} to {@link com.jelinski.niajee.motorcycle.model.MotorcycleModel}.
     *
     * @return new instance
     */
    public MotorcycleToModelFunction motorcycleToModel() {
        return new MotorcycleToModelFunction();
    }
}
