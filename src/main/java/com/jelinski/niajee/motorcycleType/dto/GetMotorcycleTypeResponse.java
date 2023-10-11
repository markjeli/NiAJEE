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

import java.util.UUID;


/**
 * GET motorcycle type response. Described details about selected motorcycle type. Can be used to present description while
 * character creation or on character's stat page. How motorcycle type is described is defined in
 * {@link com.jelinski.niajee.motorcycleType.entity.MotorcycleType}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetMotorcycleTypeResponse {

    /**
     * Unique id identifying motorcycle type.
     */
    private UUID id;

    /**
     * Name of the motorcycle type.
     */
    private String name;

    /**
     * Description of the motorcycle type.
     */
    private String description;

    /**
     * Riding position of the motorcycle type.
     */
    private EnumMotorcycleType.RidingPosition ridingPosition;
}
