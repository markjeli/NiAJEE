package com.jelinski.niajee.motorcycleType.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

/**
 * GET motorcycle type response. Returns list of all available motorcycle type names.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetMotorcycleTypesResponse {

    /**
     * Represents single motorcycle type in list.
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
        private String name;

    }

    /**
     * List of all motorcycle types.
     */
    @Singular
    private List<MotorcycleType> motorcycleTypes;

}
