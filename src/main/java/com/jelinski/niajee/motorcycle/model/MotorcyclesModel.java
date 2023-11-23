package com.jelinski.niajee.motorcycle.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * JSF view model class in order to not use entity classes. Represents list of motorcycles to be displayed.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class MotorcyclesModel implements Serializable {

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

        /**
         * Price of the motorcycle.
         */
        private int price;

        /**
         * motorcycle's version.
         */
        private Long version;

        /**
         * motorcycle's creation date.
         */
        private LocalDateTime creationDateTime;

        /**
         * motorcycle's last update date.
         */
        private LocalDateTime lastUpdateDateTime;

    }

    /**
     * Name of the selected motorcycles.
     */
    @Singular
    private List<Motorcycle> motorcycles;
}
