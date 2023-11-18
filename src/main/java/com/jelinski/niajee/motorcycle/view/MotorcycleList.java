package com.jelinski.niajee.motorcycle.view;

import com.jelinski.niajee.component.ModelFunctionFactory;
import com.jelinski.niajee.motorcycle.model.MotorcyclesModel;
import com.jelinski.niajee.motorcycle.service.MotorcycleService;
import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import com.jelinski.niajee.motorcycleType.service.MotorcycleTypeService;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

/**
 * View bean for rendering list of motorcycles.
 */
@ViewScoped
@Named
public class MotorcycleList implements Serializable {

    /**
     * Service for managing motorcycles.
     */
    private MotorcycleService motorcycleService;

    /**
     * Service for managing motorcycle types.
     */
    private MotorcycleTypeService motorcycleTypeService;

    /**
     * Motorcycles list exposed to the view.
     */
    private MotorcyclesModel motorcycles;

    /**
     * Id of the selected motorcycle type.
     */
    @Getter
    @Setter
    private UUID motorcycleTypeId;

    /**
     * Description of the selected motorcycle type.
     */
    @Getter
    @Setter
    private String motorcycleTypeDescription;

    /**
     * Factory producing functions for conversion between models and entities.
     */
    private final ModelFunctionFactory factory;


    /**
     * @param factory factory producing functions for conversion between models and entities
     */
    @Inject
    public MotorcycleList(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setMotorcycleService(MotorcycleService motorcycleService) {
        this.motorcycleService = motorcycleService;
    }

    @EJB
    public void setMotorcycleTypeService(MotorcycleTypeService motorcycleTypeService) {
        this.motorcycleTypeService = motorcycleTypeService;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached using
     * lazy getter.
     *
     * @return all motorcycles
     */
    public MotorcyclesModel getMotorcycles() {
        if (motorcycles == null) {
            if (motorcycleTypeId != null) {
                Optional<MotorcycleType> motorcycleType = motorcycleTypeService.find(motorcycleTypeId);
                motorcycles = factory.motorcyclesToModel().apply(motorcycleService.findAll(motorcycleType.get()));
            } else {
                motorcycles = factory.motorcyclesToModel().apply(motorcycleService.findAll());
            }

        }
        return motorcycles;
    }

    public void init() {
        if (motorcycleTypeId == null) {
            motorcycleTypeDescription = "All motorcycles";
        } else {
            Optional<MotorcycleType> motorcycleType = motorcycleTypeService.find(motorcycleTypeId);
            if (motorcycleType.isPresent()) {
                motorcycleTypeDescription = motorcycleType.get().getDescription();
            } else {
                motorcycleTypeDescription = "All motorcycles";
            }
        }
    }

    /**
     * Action for clicking delete action.
     *
     * @param motorcycle motorcycle to be removed
     */
    public void deleteAction(MotorcyclesModel.Motorcycle motorcycle) {
        motorcycleService.delete(motorcycle.getId());
        motorcycles = null;
    }
}
