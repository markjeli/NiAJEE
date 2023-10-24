package com.jelinski.niajee.motorcycleType.model;

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
 * JSF view model class in order to not use entity classes. Represents single motorcycle type to be displayed or selected.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class MotorcycleTypeModel {

        /**
        * MotorcycleType's id.
        */
        private UUID id;

        /**
        * Name of the motorcycleType.
        */
        private String typeName;

}
