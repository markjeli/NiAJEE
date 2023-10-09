package com.jelinski.niajee.configuration.listener;

import com.jelinski.niajee.component.DtoFunctionFactory;
import com.jelinski.niajee.user.controller.simple.UserSimpleController;
import com.jelinski.niajee.user.service.UserService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

/**
 * Listener started automatically on servlet context initialized. Creates an instance of controllers and puts them in
 * the application (servlet) context.
 */
public class CreateControllers implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        UserService userService = (UserService) event.getServletContext().getAttribute("userService");
        event.getServletContext().setAttribute("userController", new UserSimpleController(
                userService,
                new DtoFunctionFactory()
        ));
    }

}
