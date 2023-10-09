package com.jelinski.niajee.configuration.listener;

import com.jelinski.niajee.crypto.component.Pbkdf2PasswordHash;
import com.jelinski.niajee.datastore.component.DataStore;
import com.jelinski.niajee.user.repository.api.UserRepository;
import com.jelinski.niajee.user.repository.memory.UserInMemoryRepository;
import com.jelinski.niajee.user.service.UserService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

/**
 * Listener started automatically on servlet context initialized. Creates an instance of services (business layer) and
 * puts them in the application (servlet) context.
 */
public class CreateServices implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        DataStore dataSource = (DataStore) event.getServletContext().getAttribute("datasource");

        UserRepository userRepository = new UserInMemoryRepository(dataSource);

        event.getServletContext().setAttribute("userService", new UserService(userRepository, new Pbkdf2PasswordHash()));
    }

}
