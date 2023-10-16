package com.jelinski.niajee.motorcycle.dto;

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
import java.util.UUID;

/**
 * Represents single motorcycle.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetMotorcycleResponse {

    /**
     * Represents single motorcycle type.
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class MotorcycleType {
        /**
         * Unique id identifying motorcycle type.
         */
        private UUID id;

        /**
         * Name of the motorcycle type.
         */
        private String typeName;
    }

    /**
     * Unique id identifying motorcycle.
     */
    private UUID id;

    /**
     * Name of the motorcycle.
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
    private MotorcycleType motorcycleType;
}
