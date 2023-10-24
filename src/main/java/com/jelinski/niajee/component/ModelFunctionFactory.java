package com.jelinski.niajee.component;

import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.motorcycle.model.function.*;
import com.jelinski.niajee.motorcycle.model.MotorcycleEditModel;
import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import com.jelinski.niajee.motorcycle.model.MotorcyclesModel;
import com.jelinski.niajee.motorcycleType.model.function.MotorcycleTypeToModelFunction;
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
     * Returns a function to convert a single {@link MotorcycleType} to {@link com.jelinski.niajee.motorcycleType.model.MotorcycleTypeModel}.
     *
     * @return new instance
     */
    public MotorcycleTypeToModelFunction motorcycleTypeToModel() {
        return new MotorcycleTypeToModelFunction();
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

    /**
     * Returns a function to convert a single {@link Motorcycle} to {@link MotorcycleEditModel}.
     *
     * @return new instance
     */
    public MotorcycleToEditModelFunction motorcycleToEditModel() {
        return new MotorcycleToEditModelFunction();
    }

    /**
     * Returns a function to convert a single {@link MotorcycleEditModel} to {@link Motorcycle}.
     *
     * @return UpdateMotorcycleFunction instance
     */
    public UpdateMotorcycleWithModelFunction updateMotorcycle() {
        return new UpdateMotorcycleWithModelFunction();
    }

    /**
     * Returns a function to convert a single {@link Motorcycle} to {@link Motorcycle}.
     *
     * @return ModelToMotorcycleFunction instance
     */
    public ModelToMotorcycleFunction modelToMotorcycle() {
        return new ModelToMotorcycleFunction();
    }
}
