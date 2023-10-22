package com.jelinski.niajee.motorcycleType.view;

import com.jelinski.niajee.component.ModelFunctionFactory;
import com.jelinski.niajee.motorcycleType.model.MotorcycleTypesModel;
import com.jelinski.niajee.motorcycleType.service.MotorcycleTypeService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * View bean for rendering list of motorcycle types.
 */
@RequestScoped
@Named
public class MotorcycleTypeList {

    /**
     * Service for managing motorcycle types.
     */
    private final MotorcycleTypeService service;

    /**
     * Motorcycles list exposed to the view.
     */
    private MotorcycleTypesModel motorcycleTypes;

    /**
     * Factory producing functions for conversion between models and entities.
     */
    private final ModelFunctionFactory factory;

    /**
     * @param service motorcycle type service
     * @param factory factory producing functions for conversion between models and entities
     */
    @Inject
    public MotorcycleTypeList(MotorcycleTypeService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached using
     * lazy getter.
     *
     * @return all motorcycle types
     */
    public MotorcycleTypesModel getMotorcycleTypes() {
        if (motorcycleTypes == null) {
            motorcycleTypes = factory.motorcycleTypesToModel().apply(service.findAll());
        }
        return motorcycleTypes;
    }

    /**
     * Action for clicking delete action.
     *
     * @param motorcycleType motorcycle type to be removed
     * @return navigation case to list_motorcycle_types
     */
    public String deleteAction(MotorcycleTypesModel.MotorcycleType motorcycleType) {
        service.delete(motorcycleType.getId());
        return "motorcycleType_list?faces-redirect=true";
    }
}
