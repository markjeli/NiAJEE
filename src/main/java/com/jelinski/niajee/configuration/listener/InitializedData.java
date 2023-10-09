package com.jelinski.niajee.configuration.listener;

import com.jelinski.niajee.user.entity.User;
import com.jelinski.niajee.user.service.UserService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Listener started automatically on servlet context initialized. Fetches instance of the datasource from the servlet
 * context and fills it with default content. Normally this class would fetch database datasource and init data only in
 * cases of empty database. When using persistence storage application instance should be initialized only during first
 * run in order to init database with starting data. Good place to create first default admin user.
 */
public class InitializedData implements ServletContextListener {

    /**
     * User service.
     */
    private UserService userService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        userService = (UserService) event.getServletContext().getAttribute("userService");
        init();
    }

    /**
     * Initializes database with some example values. Should be called after creating this object. This object should be
     * created only once.
     */
    @SneakyThrows
    private void init() {
        User admin = User.builder()
                .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                .login("admin")
                .name("System")
                .surname("Admin")
                .birthDate(LocalDate.of(1990, 10, 21))
                .email("admin@simplerpg.example.com")
                .password("adminadmin")
                .portrait(getResourceAsByteArray("../avatar/admin.png"))
                .build();

        User kevin = User.builder()
                .id(UUID.fromString("81e1c2a9-7f57-439b-b53d-6db88b071e4e"))
                .login("kevin")
                .name("Kevin")
                .surname("Pear")
                .birthDate(LocalDate.of(2001, 1, 16))
                .email("kevin@example.com")
                .password("useruser")
                .portrait(null)
                .build();

        User alice = User.builder()
                .id(UUID.fromString("ed6cfb2a-cad7-47dd-9b56-9d1e3c7a4197"))
                .login("alice")
                .name("Alice")
                .surname("Grape")
                .birthDate(LocalDate.of(2002, 3, 19))
                .email("alice@example.com")
                .password("useruser")
                .portrait(null)
                .build();

        User bob = User.builder()
                .id(UUID.fromString("c4a04a73-f6e0-4996-a465-3b85f2ad1ea3"))
                .login("bob")
                .name("Bob")
                .surname("Orange")
                .birthDate(LocalDate.of(2003, 7, 12))
                .email("bob@example.com")
                .password("useruser")
                .portrait(null)
                .build();

        userService.create(admin);
        userService.create(kevin);
        userService.create(alice);
        userService.create(bob);
    }

    /**
     * @param name name of the desired resource
     * @return array of bytes read from the resource
     */
    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            if (is != null) {
                return is.readAllBytes();
            } else {
                throw new IllegalStateException("Unable to get resource %s".formatted(name));
            }
        }
    }

}
