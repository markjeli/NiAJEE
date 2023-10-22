package com.jelinski.niajee.motorcycle.model;

import com.jelinski.niajee.motorcycle.entity.EnumMotorcycle;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * JSF view model class in order to not use entity classes. Represents single motorcycle to be displayed.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class MotorcycleModel {

    /**
     * Motorcycle name.
     */
    private String name;

    /**
     * Motorcycle price.
     */
    private int price;

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
     * Motorcycle's weight.
     */
    private int weight;

    /**
     * Motorcycle type.
     */
    private String type;

}
