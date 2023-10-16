package com.jelinski.niajee.motorcycle.dto.function;

import com.jelinski.niajee.motorcycle.entity.EnumMotorcycle;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchMotorcycleRequest {
    /**
     * Motorcycle's name.
     */
    private String name;

    /**
     * Motorcycle's color.
     */
    private EnumMotorcycle.Color color;

    /**
     * Motorcycle's price.
     */
    private int price;

}
