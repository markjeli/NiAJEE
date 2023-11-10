package com.jelinski.niajee.user.dto.function;

import com.jelinski.niajee.user.dto.PutUserRequest;
import com.jelinski.niajee.user.entity.User;

import java.util.UUID;
import java.util.function.BiFunction;

/**
 * Converts {@link PutUserRequest} to {@link User}. Caution, password should be hashed in business logic.
 */
public class RequestToUserFunction implements BiFunction<UUID, PutUserRequest, User> {
    @Override
    public User apply(UUID uuid, PutUserRequest putUserRequest) {
        return User.builder()
                .id(uuid)
                .login(putUserRequest.getLogin())
                .name(putUserRequest.getName())
                .surname(putUserRequest.getSurname())
                .birthDate(putUserRequest.getBirthDate())
                .password(putUserRequest.getPassword())
                .email(putUserRequest.getEmail())
                .build();
    }
}
