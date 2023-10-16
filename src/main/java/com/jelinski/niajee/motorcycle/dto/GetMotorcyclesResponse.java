package com.jelinski.niajee.motorcycle.dto;

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

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetMotorcyclesResponse {

    /**
     * Represents single motorcycle in list.
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Motorcycle {
        /**
         * Unique id identifying motorcycle.
         */
        private UUID id;

        /**
         * Name of the motorcycle.
         */
        private String name;
    }

    /**
     * List of the selected motorcycles.
     */
    @Singular
    private List<Motorcycle> motorcycles;
}
