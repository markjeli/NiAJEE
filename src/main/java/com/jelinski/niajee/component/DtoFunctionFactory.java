package com.jelinski.niajee.component;

import com.jelinski.niajee.motorcycleType.dto.function.MotorcycleTypeToResponseFunction;
import com.jelinski.niajee.motorcycleType.dto.GetMotorcycleTypeResponse;
import com.jelinski.niajee.motorcycleType.dto.GetMotorcycleTypesResponse;
import com.jelinski.niajee.motorcycleType.dto.function.MotorcycleTypesToResponseFunction;
import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import com.jelinski.niajee.user.dto.function.UserToResponseFunction;
import com.jelinski.niajee.user.dto.GetUserResponse;
import com.jelinski.niajee.user.dto.GetUsersResponse;
import com.jelinski.niajee.user.dto.function.UsersToResponseFunction;
import com.jelinski.niajee.user.entity.User;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.function.Function;

/**
 * Factor for creating {@link Function} implementation for converting between various objects used in different layers.
 * Instead of injecting multiple function objects single factory is injected.
 */
@ApplicationScoped
public class DtoFunctionFactory {

    /**
     * Returns a function to convert a single {@link User} to {@link GetUserResponse}.
     *
     * @return UserToResponseFunction instance
     */
    public UserToResponseFunction userToResponse() {
        return new UserToResponseFunction();
    }

    /**
     * Returns a function to convert a list of {@link User} to {@link GetUsersResponse}.
     *
     * @return UsersToResponseFunction instance
     */
    public UsersToResponseFunction usersToResponse() {
        return new UsersToResponseFunction();
    }

    /**
     * Returns a function to convert a single {@link MotorcycleType} to {@link GetMotorcycleTypeResponse}.
     *
     * @return MotorcycleTypeToResponseFunction instance
     */
    public MotorcycleTypeToResponseFunction motorcycleTypeToResponse() {
        return new MotorcycleTypeToResponseFunction();
    }

    /**
     * Returns a function to convert a list of {@link MotorcycleType} to {@link GetMotorcycleTypesResponse}.
     */
    public MotorcycleTypesToResponseFunction motorcycleTypesToResponse() {
        return new MotorcycleTypesToResponseFunction();
    }

}
