package com.jelinski.niajee.user.model.function;

import com.jelinski.niajee.user.entity.User;
import com.jelinski.niajee.user.model.UsersModel;

import java.util.List;
import java.util.function.Function;

/**
 * Converts {@link List<User>} to {@link UsersModel}.
 */
public class UsersToModelFunction implements Function<List<User>, UsersModel> {

    @Override
    public UsersModel apply(List<User> entity) {
        return UsersModel.builder()
                .users(entity.stream()
                        .map(character -> UsersModel.User.builder()
                                .id(character.getId())
                                .login(character.getLogin())
                                .build())
                        .toList())
                .build();
    }

}
