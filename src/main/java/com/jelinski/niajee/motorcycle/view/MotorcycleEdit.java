package com.jelinski.niajee.motorcycle.view;

import com.jelinski.niajee.component.ModelFunctionFactory;
import com.jelinski.niajee.motorcycle.entity.EnumMotorcycle;
import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.motorcycle.model.MotorcycleEditModel;
import com.jelinski.niajee.motorcycle.service.MotorcycleService;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

/**
 * View bean for rendering single motorcycle edit form.
 */
@ViewScoped
@Named
public class MotorcycleEdit implements Serializable {

    /**
     * Service for managing motorcycles.
     */
    private MotorcycleService service;

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
     * Motorcycle color.
     */
    @Setter
    @Getter
    private EnumMotorcycle.Color[] colors;

    /**
     * Motorcycle exposed to the view.
     */
    @Getter
    private MotorcycleEditModel motorcycle;

    /**
     * @param factory factory producing functions for conversion between models and entities
     */
    @Inject
    public MotorcycleEdit(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(MotorcycleService service) {
        this.service = service;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached within
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        this.colors = EnumMotorcycle.Color.values();
        Optional<Motorcycle> motorcycle = service.find(id);
        if (motorcycle.isPresent()) {
            this.motorcycle = factory.motorcycleToEditModel().apply(motorcycle.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Motorcycle not found");
        }
    }

    /**
     * Action initiated by clicking save button.
     *
     * @return navigation case to the same page
     */
    public String saveAction() throws IOException {
        try {
            service.update(factory.updateMotorcycle().apply(service.find(id).orElseThrow(), motorcycle));
            String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            return viewId + "?faces-redirect=true&includeViewParams=true";
        } catch (EJBException ex) {
            if (ex.getCause() instanceof OptimisticLockException) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Version collision."));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Your previous version:"));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Name - " + motorcycle.getName()));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Price - " + motorcycle.getPrice()));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Color - " + motorcycle.getColor()));
                init();
            }
            return null;
        }

    }

}
