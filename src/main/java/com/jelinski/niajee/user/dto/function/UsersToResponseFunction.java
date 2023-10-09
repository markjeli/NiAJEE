package com.jelinski.niajee.user.dto.function;

import com.jelinski.niajee.user.dto.GetUsersResponse;
import com.jelinski.niajee.user.entity.User;

import java.util.List;
import java.util.function.Function;

/**
 * Converts {@link List<User>} to {@link GetUsersResponse}.
 */
public class UsersToResponseFunction implements Function<List<User>, GetUsersResponse> {

    @Override
    public GetUsersResponse apply(List<User> users) {
        return GetUsersResponse.builder()
                .users(users.stream()
                        .map(user -> GetUsersResponse.User.builder()
                                .id(user.getId())
                                .login(user.getLogin())
                                .build())
                        .toList())
                .build();
    }

}
