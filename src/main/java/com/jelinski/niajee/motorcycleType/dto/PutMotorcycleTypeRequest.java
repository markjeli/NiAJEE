package com.jelinski.niajee.motorcycleType.dto;

import com.jelinski.niajee.motorcycleType.entity.EnumMotorcycleType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * PUT motorcycleType request. Contains only fields that can be set up byt the user while creating a new motorcycleType.How
 * motorcycleType is described is defined in {@link com.jelinski.niajee.motorcycleType.dto.GetMotorcycleTypesResponse.MotorcycleType} and
 * {@link com.jelinski.niajee.motorcycleType.entity.MotorcycleType} classes.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutMotorcycleTypeRequest {

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


}
