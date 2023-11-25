package com.jelinski.niajee.motorcycle.view;

import com.jelinski.niajee.component.ModelFunctionFactory;
import com.jelinski.niajee.motorcycle.entity.EnumMotorcycle;
import com.jelinski.niajee.motorcycle.model.MotorcycleCreateModel;
import com.jelinski.niajee.motorcycle.model.MotorcycleFilterModel;
import com.jelinski.niajee.motorcycle.model.MotorcyclesModel;
import com.jelinski.niajee.motorcycle.service.MotorcycleService;
import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import com.jelinski.niajee.motorcycleType.model.MotorcycleTypeModel;
import com.jelinski.niajee.motorcycleType.service.MotorcycleTypeService;
import com.jelinski.niajee.user.entity.User;
import com.jelinski.niajee.user.model.UserModel;
import com.jelinski.niajee.user.service.UserService;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
     * Service for managing users.
     */
    private UserService userService;

    /**
     * Motorcycles list exposed to the view.
     */
    private MotorcyclesModel motorcycles;

    /**
     * Available motorcycleTypes.
     */
    @Getter
    private List<MotorcycleTypeModel> motorcycleTypes;

    /**
     * Users list exposed to the view.
     */
    @Getter
    private List<UserModel> users;

    /**
     * Motorcycle exposed to the view.
     */
    @Getter
    @Setter
    private MotorcycleFilterModel motorcycleFilter;

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

    @EJB
    public void setUserService(UserService userService) {
        this.userService = userService;
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
            }
//            else {
//                motorcycles = factory.motorcyclesToModel().apply(motorcycleService.findAll());
//            }

        }
        return motorcycles;
    }

    public void init() {
        if (motorcycleTypeId == null) {
            this.colors = EnumMotorcycle.Color.values();
            this.brands = EnumMotorcycle.Brand.values();
            motorcycleTypeDescription = "All motorcycles";
            motorcycleTypes = motorcycleTypeService.findAll().stream()
                    .map(factory.motorcycleTypeToModel())
                    .collect(Collectors.toList());
            users = userService.findAll().stream()
                    .map(factory.userToModel())
                    .collect(Collectors.toList());
            motorcycleFilter = MotorcycleFilterModel.builder().build();
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

    /**
     * Action for clicking filter action.
     */
    public void filter() {
        motorcycles = factory.motorcyclesToModel().apply(motorcycleService.findAllFiltered(
                        motorcycleFilter.getName(),
                        motorcycleFilter.getHorsepower(),
                        motorcycleFilter.getColor(),
                        motorcycleFilter.getBrand(),
                        motorcycleFilter.getProductionDate(),
                        motorcycleFilter.getPrice(),
                        motorcycleFilter.getWeight(),
                        motorcycleTypeService.find(motorcycleFilter.getMotorcycleType().getId()).get(),
                        userService.find(motorcycleFilter.getUser().getId()).get()
                )
        );
        if (motorcycles != null && motorcycles.getMotorcycles().isEmpty()) {
            motorcycles = factory.motorcyclesToModel().apply(motorcycleService.findAll());
        }
    }
}
