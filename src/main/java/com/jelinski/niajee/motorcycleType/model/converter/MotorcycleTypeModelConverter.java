package com.jelinski.niajee.motorcycleType.model.converter;

import com.jelinski.niajee.component.ModelFunctionFactory;
import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import com.jelinski.niajee.motorcycleType.model.MotorcycleTypeModel;
import com.jelinski.niajee.motorcycleType.service.MotorcycleTypeService;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.UUID;

@FacesConverter(forClass = MotorcycleTypeModel.class, managed = true)
public class MotorcycleTypeModelConverter implements Converter<MotorcycleTypeModel> {

    private final MotorcycleTypeService service;

    private final ModelFunctionFactory factory;

    @Inject
    public MotorcycleTypeModelConverter(MotorcycleTypeService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public MotorcycleTypeModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<MotorcycleType> motorcycleType = service.find(UUID.fromString(value));
        return motorcycleType.map(factory.motorcycleTypeToModel()).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, MotorcycleTypeModel value) {
        return value == null ? "" : value.getId().toString();
    }

}
