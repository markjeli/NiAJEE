package com.jelinski.niajee.configuration.listener;

import com.jelinski.niajee.datastore.component.DataStore;
import com.jelinski.niajee.serialization.component.CloningUtility;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

/**
 * Listener started automatically on servlet context initialized. Creates an instance of datasource and puts it in the
 * application (servlet) context.
 */
public class CreateDataSource implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        event.getServletContext().setAttribute("datasource", new DataStore(new CloningUtility()));
    }

}
