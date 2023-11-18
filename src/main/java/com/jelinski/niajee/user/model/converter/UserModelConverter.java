package com.jelinski.niajee.user.model.converter;

import com.jelinski.niajee.component.ModelFunctionFactory;
import com.jelinski.niajee.user.entity.User;
import com.jelinski.niajee.user.model.UserModel;
import com.jelinski.niajee.user.service.UserService;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.UUID;


@FacesConverter(forClass = UserModel.class, managed = true)
public class UserModelConverter implements Converter<UserModel> {

    private final UserService service;

    private final ModelFunctionFactory factory;

    @Inject
    public UserModelConverter(UserService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public UserModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<User> user = service.find(UUID.fromString(value));
        return user.map(factory.userToModel()).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, UserModel value) {
        return value == null ? "" : value.getId().toString();
    }
}
