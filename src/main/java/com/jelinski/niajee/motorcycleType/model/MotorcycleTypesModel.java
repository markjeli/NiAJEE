package com.jelinski.niajee.motorcycleType.model;

import com.jelinski.niajee.motorcycle.model.MotorcyclesModel;
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
import java.util.List;
import java.util.UUID;

/**
 * JSF view model class in order to not use entity classes. Represents list of motorcycle types to be displayed.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class MotorcycleTypesModel implements Serializable {

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
        private String typeName;

    }

    /**
     * Name of the selected motorcycle types.
     */
    @Singular
    private List<MotorcycleType> motorcycleTypes;

}
