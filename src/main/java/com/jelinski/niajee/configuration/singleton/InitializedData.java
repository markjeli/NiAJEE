package com.jelinski.niajee.configuration.singleton;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import com.jelinski.niajee.motorcycle.entity.EnumMotorcycle;
import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.motorcycle.service.MotorcycleService;
import com.jelinski.niajee.motorcycleType.entity.EnumMotorcycleType;
import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import com.jelinski.niajee.motorcycleType.service.MotorcycleTypeService;
import com.jelinski.niajee.user.entity.User;
import com.jelinski.niajee.user.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Listener started automatically on CDI application context initialized. Injects proxy to the services and fills
 * database with default content. When using persistence storage application instance should be initialized only during
 * first run in order to init database with starting data. Good place to create first default admin user.
 */
@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
@NoArgsConstructor
public class InitializedData {

    /**
     * User service.
     */
    private UserService userService;

    /**
     * Motorcycle type service.
     */
    private MotorcycleTypeService motorcycleTypeService;

    /**
     * Motorcycle service.
     */
    private MotorcycleService motorcycleService;

    /**
     * @param userService User service.
     */
    @EJB
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param motorcycleTypeService Motorcycle type service.
     */
    @EJB
    public void setMotorcycleTypeService(MotorcycleTypeService motorcycleTypeService) {
        this.motorcycleTypeService = motorcycleTypeService;
    }

    /**
     * @param motorcycleService Motorcycle service.
     */
    @EJB
    public void setMotorcycleService(MotorcycleService motorcycleService) {
        this.motorcycleService = motorcycleService;
    }

    /**
     * Initializes database with some example values. Should be called after creating this object. This object should be
     * created only once.
     */
    @PostConstruct
    @SneakyThrows
    private void init() {
        if (userService.find("admin").isEmpty()) {

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

            Motorcycle kawasaki = Motorcycle.builder()
                    .id(UUID.fromString("d8422238-ce90-4c9b-87ec-cd75f2bb32f3"))
                    .name("Kawasaki Z900")
                    .horsepower(125)
                    .color(EnumMotorcycle.Color.GREEN)
                    .brand(EnumMotorcycle.Brand.KAWASAKI)
                    .productionDate(LocalDate.of(2017, 1, 1))
                    .price(40000)
                    .weight(200)
                    .motorcycleType(naked)
                    .user(alice)
                    .build();

            Motorcycle yamaha = Motorcycle.builder()
                    .id(UUID.fromString("c2776082-6691-44e8-a9d9-1f405637d226"))
                    .name("Yamaha MT-09")
                    .horsepower(115)
                    .color(EnumMotorcycle.Color.BLUE)
                    .brand(EnumMotorcycle.Brand.YAMAHA)
                    .productionDate(LocalDate.of(2013, 1, 1))
                    .price(35000)
                    .weight(245)
                    .motorcycleType(naked)
                    .user(bob)
                    .build();

            Motorcycle honda = Motorcycle.builder()
                    .id(UUID.fromString("289b0034-2a5f-49e1-8701-81d5b3cf63b9"))
                    .name("Honda CBR 1000RR")
                    .horsepower(200)
                    .color(EnumMotorcycle.Color.RED)
                    .brand(EnumMotorcycle.Brand.HONDA)
                    .productionDate(LocalDate.of(2017, 1, 1))
                    .price(60000)
                    .weight(195)
                    .motorcycleType(sport)
                    .user(kevin)
                    .build();

            motorcycleService.create(kawasaki);
            motorcycleService.create(yamaha);
            motorcycleService.create(honda);
        }

    }

}
