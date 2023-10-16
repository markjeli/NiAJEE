package com.jelinski.niajee.motorcycle.dto.function;

import com.jelinski.niajee.motorcycle.dto.GetMotorcyclesResponse;
import com.jelinski.niajee.motorcycle.entity.Motorcycle;

import java.util.List;
import java.util.function.Function;

public class MotorcyclesToResponseFunction implements Function<List<Motorcycle>, GetMotorcyclesResponse> {

    @Override
    public GetMotorcyclesResponse apply(List<Motorcycle> entities) {
        return GetMotorcyclesResponse.builder()
                .motorcycles(entities.stream()
                        .map(motorcycle -> GetMotorcyclesResponse.Motorcycle.builder()
                                .id(motorcycle.getId())
                                .name(motorcycle.getName())
                                .build())
                        .toList())
                .build();
    }
}
