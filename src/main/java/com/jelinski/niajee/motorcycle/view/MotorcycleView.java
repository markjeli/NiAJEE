package com.jelinski.niajee.motorcycle.view;

import com.jelinski.niajee.component.ModelFunctionFactory;
import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.motorcycle.model.MotorcycleModel;
import com.jelinski.niajee.motorcycle.service.MotorcycleService;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

/**
 * View bean for rendering single motorcycle information.
 */
@ViewScoped
@Named
public class MotorcycleView implements Serializable {

    /**
     * Service for managing motorcycles.
     */
    private final MotorcycleService service;

    /**
     * Factory producing functions for conversion between models and entities.
     */
    private final ModelFunctionFactory factory;

    /**
     * Motorcycle id.
     */
    @Setter
    @Getter
    private UUID id;

    /**
     * Motorcycle exposed to the view.
     */
    @Getter
    private MotorcycleModel motorcycle;

    /**
     * @param service service for managing motorcycles
     * @param factory factory producing functions for conversion between models and entities
     */
    @Inject
    public MotorcycleView(MotorcycleService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached within
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        Optional<Motorcycle> motorcycle = service.find(id);
        if (motorcycle.isPresent()) {
            this.motorcycle = factory.motorcycleToModel().apply(motorcycle.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Motorcycle not found");
        }
    }

}
