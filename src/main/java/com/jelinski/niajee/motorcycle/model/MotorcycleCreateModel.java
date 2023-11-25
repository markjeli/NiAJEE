package com.jelinski.niajee.motorcycle.model;

import com.jelinski.niajee.motorcycle.entity.EnumMotorcycle;
import com.jelinski.niajee.motorcycle.validation.binding.ValidMotorcyclePrice;
import com.jelinski.niajee.motorcycleType.model.MotorcycleTypeModel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

/**
 * JSF view model class in order to not use entity classes. Represents new motorcycle to be created. Includes oll
 * fields which can be used in motorcycle creation.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class MotorcycleCreateModel {

    /**
     * Unique id (primary key).
     */
    private UUID id;

    /**
     * Motorcycle's name.
     */
    @NotBlank
    private String name;

    /**
     * Motorcycle's horsepower.
     */
    @Min(1)
    @NotNull
    private int horsepower;

    /**
     * Motorcycle's color.
     */
    @NotNull
    private EnumMotorcycle.Color color;

    /**
     * Motorcycle's brand.
     */
    @NotNull
    private EnumMotorcycle.Brand brand;

    /**
     * Motorcycle's production date.
     */
    @Past
    @NotNull
    private LocalDate productionDate;

    /**
     * Motorcycle's price.
     */
    @Min(1)
    @NotNull
    @ValidMotorcyclePrice
    private int price;

    /**
     * Motorcycle's weight.
     */
    @Min(1)
    @NotNull
    private int weight;

    /**
     * Motorcycle's type.
     */
    @NotNull
    private MotorcycleTypeModel motorcycleType;

}
