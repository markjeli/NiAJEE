package com.jelinski.niajee.motorcycle.entity;

import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import com.jelinski.niajee.user.entity.User;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@Entity
@Table(name = "motorcycles")
public class Motorcycle implements Serializable {
    /**
     * Unique id (primary key).
     */
    @Id
    private UUID id;

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
    @Enumerated(EnumType.STRING)
    private EnumMotorcycle.Color color;

    /**
     * Motorcycle's brand.
     */
    @Enumerated(EnumType.STRING)
    private EnumMotorcycle.Brand brand;

    /**
     * Motorcycle's production date.
     */
    @Column(name = "production_date")
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
    @ManyToOne
    @JoinColumn(name = "motorcycle_type")
    private MotorcycleType motorcycleType;

    /**
     * Owner of this motorcycle.
     */
    @ManyToOne
    @JoinColumn(name = "user_name")
    private User user;

}
