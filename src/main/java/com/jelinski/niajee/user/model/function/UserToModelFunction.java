package com.jelinski.niajee.user.model.function;

import com.jelinski.niajee.user.entity.User;
import com.jelinski.niajee.user.model.UserModel;


import java.io.Serializable;
import java.util.function.Function;

/**
 * Converts {@link User} to {@link UserModel}.
 */
public class UserToModelFunction implements Function<User, UserModel>, Serializable {

    @Override
    public UserModel apply(User entity) {
        return UserModel.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .build();
    }

}
