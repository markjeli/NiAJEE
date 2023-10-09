package com.jelinski.niajee.motorcycleType.entity;

import com.jelinski.niajee.motorcycle.entity.Motorcycle;
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
public class MotorcycleType implements Serializable {

    /**
     * Unique id (primary key).
     */
    private UUID id;

    /**
     * Name of the motorcycle type.
     */
    private String typeName;

    /**
     * Description of the motorcycle type.
     */
    private String description;

    /**
     * Riding position of the motorcycle type.
     */
    private EnumMotorcycleType.RidingPosition ridingPosition;

    /**
     * List of motorcycles of this type.
     */
    List<Motorcycle> motorcycles;

}
