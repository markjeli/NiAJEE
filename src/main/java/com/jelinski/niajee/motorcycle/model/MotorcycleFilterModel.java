package com.jelinski.niajee.motorcycle.model;

import com.jelinski.niajee.motorcycle.entity.EnumMotorcycle;
import com.jelinski.niajee.motorcycleType.model.MotorcycleTypeModel;
import com.jelinski.niajee.user.model.UserModel;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class MotorcycleFilterModel {

    /**
     * Motorcycle's name.
     */
    private String name;

    /**
     * Motorcycle's horsepower.
     */
    private int horsepower;

    /**
     * Motorcycle's color.
     */
    private EnumMotorcycle.Color color;

    /**
     * Motorcycle's brand.
     */
    private EnumMotorcycle.Brand brand;

    /**
     * Motorcycle's production date.
     */
    private LocalDate productionDate;

    /**
     * Motorcycle's price.
     */
    private int price;

    /**
     * Motorcycle's weight.
     */
    private int weight;

    /**
     * Motorcycle's type.
     */
    private MotorcycleTypeModel motorcycleType;

    /**
     * Motorcycle's user.
     */
    private UserModel user;

}
