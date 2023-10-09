package com.jelinski.niajee.motorcycle.entity;

import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import com.jelinski.niajee.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class Motorcycle implements Serializable {
    /**
     * Unique id (primary key).
     */
    private UUID id;

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
    private Date productionDate;

    /**
     * Motorcycle's price.
     */
    private int price;

    /**
     * Motorcycle's weight.
     */
    private int weight;

    /**
     * Motorcycle's image. Images in database are stored as blobs (binary large objects).
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] image;

    /**
     * Motorcycle's type.
     */
    private MotorcycleType motorcycleType;

    /**
     * Owner of this motorcycle.
     */
    private User user;

}
