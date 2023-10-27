package com.jelinski.niajee.motorcycleType.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * PATCH motorcycleType request. Contains all fields that can be updated by the user. How motorcycleType is described is defined
 * in {@link com.jelinski.niajee.motorcycleType.dto.GetMotorcycleTypesResponse.MotorcycleType} and
 * {@link com.jelinski.niajee.motorcycleType.entity.MotorcycleType} classes.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchMotorcycleTypeRequest {

    /**
     * Name of the motorcycle type.
     */
    private String typeName;

    /**
     * Description of the motorcycle type.
     */
    private String description;

}
