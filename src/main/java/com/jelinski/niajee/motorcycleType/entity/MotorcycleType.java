package com.jelinski.niajee.motorcycleType.entity;

import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "motorcycle_types")
public class MotorcycleType implements Serializable {

    /**
     * Unique id (primary key).
     */
    @Id
    private UUID id;

    /**
     * Name of the motorcycle type.
     */
    @Column(name = "type_name")
    private String typeName;

    /**
     * Description of the motorcycle type.
     */
    private String description;

    /**
     * Riding position of the motorcycle type.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "riding_position")
    private EnumMotorcycleType.RidingPosition ridingPosition;

    /**
     * List of motorcycles of this type.
     */
    @ToString.Exclude//It's common to exclude lists from toString
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "motorcycleType", cascade = CascadeType.REMOVE)
    List<Motorcycle> motorcycles;

}
