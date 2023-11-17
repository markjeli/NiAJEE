package com.jelinski.niajee.motorcycle.view;

import com.jelinski.niajee.component.ModelFunctionFactory;
import com.jelinski.niajee.motorcycle.entity.EnumMotorcycle;
import com.jelinski.niajee.motorcycle.model.MotorcycleCreateModel;
import com.jelinski.niajee.motorcycleType.model.MotorcycleTypeModel;
import com.jelinski.niajee.motorcycle.service.MotorcycleService;
import com.jelinski.niajee.motorcycleType.service.MotorcycleTypeService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * View bean for rendering single motorcycle create form. In order to use single bean, conversation scope is used.
 */
@ViewScoped
@Named
@NoArgsConstructor(force = true)
public class MotorcycleCreate implements Serializable {

    /**
     * Service for managing motorcycles.
     */
    private MotorcycleService motorcycleService;

    /**
     * Service for managing motorcycleTypes.
     */
    private MotorcycleTypeService motorcycleTypeService;

    /**
     * Factory producing functions for conversion between models and entities.
     */
    private final ModelFunctionFactory factory;

    /**
     * Motorcycle exposed to the view.
     */
    @Getter
    private MotorcycleCreateModel motorcycle;

    /**
     * Available motorcycleTypes.
     */
    @Getter
    private List<MotorcycleTypeModel> motorcycleTypes;

    /**
     * Motorcycle color.
     */
    @Setter
    @Getter
    private EnumMotorcycle.Color[] colors;

    /**
     * Motorcycle brand.
     */
    @Setter
    @Getter
    private EnumMotorcycle.Brand[] brands;

    /**
     * @param factory               factory producing functions for conversion between models and entities
     */
    @Inject
    public MotorcycleCreate(ModelFunctionFactory factory) {
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
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached within
     * field and initialized during init of the view. @PostConstruct method is called after h:form header is already
     * rendered.
     */
    public void init() {
        this.colors = EnumMotorcycle.Color.values();
        this.brands = EnumMotorcycle.Brand.values();
        motorcycleTypes = motorcycleTypeService.findAll().stream()
                .map(factory.motorcycleTypeToModel())
                .collect(Collectors.toList());
        motorcycle = MotorcycleCreateModel.builder()
                .id(UUID.randomUUID())
                .build();
    }

    /**
     * Stores new motorcycle.
     *
     * @return motorcycles list navigation case
     */
    public String saveAction() {
        motorcycleService.createForCallerPrincipal(factory.modelToMotorcycle().apply(motorcycle));
        return "/motorcycle/motorcycle_list.xhtml?faces-redirect=true";
    }

    /**
     * Cancels motorcycle creation process.
     *
     * @return characters list navigation case
     */
    public String cancelAction() {
        return "/motorcycle/motorcycle_list.xhtml?faces-redirect=true";
    }

}
