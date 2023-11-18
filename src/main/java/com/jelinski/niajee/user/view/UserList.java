package com.jelinski.niajee.user.view;

import com.jelinski.niajee.component.ModelFunctionFactory;
import com.jelinski.niajee.user.model.UsersModel;
import com.jelinski.niajee.user.service.UserService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * View bean for rendering list of users.
 */
@RequestScoped
@Named
public class UserList {

    /**
     * Service for managing users.
     */
    private UserService service;

    /**
     * Characters list exposed to the view.
     */
    private UsersModel users;

    /**
     * Factory producing functions for conversion between models and entities.
     */
    private final ModelFunctionFactory factory;

    /**
     * @param factory factory producing functions for conversion between models and entities
     */
    @Inject
    public UserList(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(UserService service) {
        this.service = service;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached using
     * lazy getter.
     *
     * @return all users
     */
    public UsersModel getUsers() {
        if (users == null) {
            users = factory.usersToModel().apply(service.findAll());
        }
        return users;
    }

    /**
     * Action for clicking delete action.
     *
     * @param user user to be removed
     */
    public void deleteAction(UsersModel.User user) {
        service.delete(user.getId());
        users = null;
    }

}

