package com.jelinski.niajee.configuration.observer;

import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import com.jelinski.niajee.motorcycleType.entity.EnumMotorcycleType;
import com.jelinski.niajee.user.entity.User;
import com.jelinski.niajee.user.service.UserService;
import com.jelinski.niajee.motorcycleType.service.MotorcycleTypeService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Listener started automatically on CDI application context initialized. Injects proxy to the services and fills
 * database with default content. When using persistence storage application instance should be initialized only during
 * first run in order to init database with starting data. Good place to create first default admin user.
 */
@ApplicationScoped
public class InitializedData {

    /**
     * User service.
     */
    private final UserService userService;

    /**
     * Motorcycle type service.
     */
    private final MotorcycleTypeService motorcycleTypeService;

    /**
     * The CDI container provides a built-in instance of {@link RequestContextController} that is dependent scoped for
     * the purposes of activating and deactivating.
     */
    private final RequestContextController requestContextController;

    /**
     * @param userService              user service
     * @param requestContextController CDI request context controller
     * @param motorcycleTypeService    motorcycle type service
     */
    @Inject
    public InitializedData(UserService userService, MotorcycleTypeService motorcycleTypeService, RequestContextController requestContextController) {
        this.userService = userService;
        this.motorcycleTypeService = motorcycleTypeService;
        this.requestContextController = requestContextController;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    /**
     * Initializes database with some example values. Should be called after creating this object. This object should be
     * created only once.
     */
    @SneakyThrows
    private void init() {
        requestContextController.activate();// start request scope in order to inject request scoped repositories

        User admin = User.builder()
                .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                .login("admin")
                .name("System")
                .surname("Admin")
                .birthDate(LocalDate.of(1990, 10, 21))
                .email("admin@simplerpg.example.com")
                .password("adminadmin")
                .build();

        User kevin = User.builder()
                .id(UUID.fromString("81e1c2a9-7f57-439b-b53d-6db88b071e4e"))
                .login("kevin")
                .name("Kevin")
                .surname("Pear")
                .birthDate(LocalDate.of(2001, 1, 16))
                .email("kevin@example.com")
                .password("useruser")
                .build();

        User alice = User.builder()
                .id(UUID.fromString("ed6cfb2a-cad7-47dd-9b56-9d1e3c7a4197"))
                .login("alice")
                .name("Alice")
                .surname("Grape")
                .birthDate(LocalDate.of(2002, 3, 19))
                .email("alice@example.com")
                .password("useruser")
                .build();

        User bob = User.builder()
                .id(UUID.fromString("c4a04a73-f6e0-4996-a465-3b85f2ad1ea3"))
                .login("bob")
                .name("Bob")
                .surname("Orange")
                .birthDate(LocalDate.of(2003, 7, 12))
                .email("bob@example.com")
                .password("useruser")
                .build();

        userService.create(admin);
        userService.updatePortrait(admin.getId(), this.getClass().getResourceAsStream("../avatar/admin.png"));
        userService.create(kevin);
        userService.create(alice);
        userService.create(bob);

        MotorcycleType naked = MotorcycleType.builder()
                .id(UUID.fromString("47fc4252-9c4e-4b61-8042-05648be14bc0"))
                .typeName("Naked")
                .description("Naked motorcycles are stripped of all fairings and bodywork that normally hide the engine and inner workings of the bike, and are characterized by their high-mounted, wide handlebars and aggressive riding stance.")
                .ridingPosition(EnumMotorcycleType.RidingPosition.UPRIGHT)
                .build();

        MotorcycleType sport = MotorcycleType.builder()
                .id(UUID.fromString("fe6946e8-db8c-4ffe-ac7a-b457372e65aa"))
                .typeName("Sport")
                .description("Sport bikes are the speed machines of the motorcycle world. High-powered with sophisticated suspension systems and high-performance brakes, sport bikes typically are stuffed with the latest and greatest technology you can find on two wheels (or four).")
                .ridingPosition(EnumMotorcycleType.RidingPosition.LEAN_FORWARD)
                .build();

        MotorcycleType cruiser = MotorcycleType.builder()
                .id(UUID.fromString("bbb60000-1a7d-45cb-b6ac-3812c8f8eb3d"))
                .typeName("Cruiser")
                .description("Cruisers are modeled after large American machines from the 1930s to 1960s, such as those made by Harley-Davidson, Indian, and Excelsior-Henderson. They are characterized by their low seat heights, wide handlebars, and forward footpegs.")
                .ridingPosition(EnumMotorcycleType.RidingPosition.LAID_BACK)
                .build();


        motorcycleTypeService.create(naked);
        motorcycleTypeService.create(sport);
        motorcycleTypeService.create(cruiser);

        requestContextController.deactivate();
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
