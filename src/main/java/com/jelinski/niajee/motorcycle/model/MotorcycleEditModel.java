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

/**
 * JSF view model class in order to not use entity classes. Represents single motorcycle to be edited. Includes
 * only fields which can be edited after motorcycle creation.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class MotorcycleEditModel {

/**
     * Name of the motorcycle.
     */
    private String name;

    /**
     * Motorcycle's price.
     */
    private int price;

    /**
     * Motorcycle's color.
     */
    private EnumMotorcycle.Color color;

}
