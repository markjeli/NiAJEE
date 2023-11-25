package com.jelinski.niajee.motorcycle.service;

import com.jelinski.niajee.motorcycle.entity.EnumMotorcycle;
import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.motorcycle.interceptor.binding.LogMethodCall;
import com.jelinski.niajee.motorcycle.repository.api.MotorcycleRepository;
import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import com.jelinski.niajee.motorcycleType.repository.api.MotorcycleTypeRepository;
import com.jelinski.niajee.user.entity.User;
import com.jelinski.niajee.user.entity.UserRoles;
import com.jelinski.niajee.user.repository.api.UserRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for all business actions regarding motorcycle entity.
 */
@LocalBean
@Stateless
@NoArgsConstructor(force = true)
@Log
public class MotorcycleService {

    /**
     * Repository for motorcycle entity.
     */
    private final MotorcycleRepository motorcycleRepository;

    /**
     * Repository for motorcycleType entity.
     */
    private final MotorcycleTypeRepository motorcycleTypeRepository;

    /**
     * Repository for user entity.
     */
    private final UserRepository userRepository;

    /**
     * Security context
     */
    private final SecurityContext securityContext;

    /**
     * @param motorcycleRepository     repository for motorcycle entity
     * @param motorcycleTypeRepository repository for motorcycleType entity
     * @param userRepository           repository for user entity
     * @param securityContext          security context
     */
    @Inject
    public MotorcycleService(
            MotorcycleRepository motorcycleRepository,
            MotorcycleTypeRepository motorcycleTypeRepository,
            UserRepository userRepository,
            @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext
    ) {
        this.motorcycleRepository = motorcycleRepository;
        this.motorcycleTypeRepository = motorcycleTypeRepository;
        this.userRepository = userRepository;
        this.securityContext = securityContext;
    }

    /**
     * Finds single motorcycle.
     *
     * @param id motorcycle's id
     * @return container with motorcycle
     */
    @RolesAllowed(UserRoles.USER)
    public Optional<Motorcycle> find(UUID id) {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return motorcycleRepository.find(id);
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return motorcycleRepository.findByIdAndUser(id, user);
    }

    /**
     * @param id   character's id
     * @param user existing user
     * @return selected character for user
     */
    @RolesAllowed(UserRoles.USER)
    public Optional<Motorcycle> find(User user, UUID id) {
        return motorcycleRepository.findByIdAndUser(id, user);
    }

    /**
     * @return all available motorcycles
     */
    @RolesAllowed(UserRoles.USER)
    public List<Motorcycle> findAll() {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return motorcycleRepository.findAll();
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return motorcycleRepository.findAllByUser(user);
    }

    /**
     * @return all available motorcycles by motorcycle type
     */
    @RolesAllowed(UserRoles.USER)
    public List<Motorcycle> findAll(MotorcycleType motorcycleType) {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return motorcycleRepository.findAllByMotorcycleType(motorcycleType);
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return motorcycleRepository.findAllByMotorcycleTypeAndUser(motorcycleType, user);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public Optional<List<Motorcycle>> findAllByMotorcycleType(UUID id) {
        return motorcycleTypeRepository.find(id)
                .map(motorcycleRepository::findAllByMotorcycleType);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public Optional<List<Motorcycle>> findAllByUser(UUID id) {
        return userRepository.find(id)
                .map(motorcycleRepository::findAllByUser);
    }

    @RolesAllowed(UserRoles.USER)
    public List<Motorcycle> findAllFiltered(
            String name,
            int horsepower,
            EnumMotorcycle.Color color,
            EnumMotorcycle.Brand brand,
            LocalDate productionDate,
            int price,
            int weight,
            MotorcycleType motorcycleType,
            User user
    ) {
        return motorcycleRepository.findAllFiltered(
                name,
                horsepower,
                color,
                brand,
                productionDate,
                price,
                weight,
                motorcycleType,
                user
        );
    }

    /**
     * Stores new motorcycle in the data store.
     *
     * @param motorcycle new motorcycle to be saved
     */
    @RolesAllowed(UserRoles.ADMIN)
    public void create(Motorcycle motorcycle) {
        if (motorcycleRepository.find(motorcycle.getId()).isPresent()) {
            throw new IllegalArgumentException("Motorcycle already exists");
        }
        if (motorcycleTypeRepository.find(motorcycle.getMotorcycleType().getId()).isEmpty()) {
            throw new IllegalArgumentException("Motorcycle type does not exist");
        }

        motorcycleRepository.create(motorcycle);
        motorcycleTypeRepository.find(motorcycle.getMotorcycleType().getId())
                .ifPresent(motorcycleType -> motorcycleType.getMotorcycles().add(motorcycle));
        userRepository.find(motorcycle.getUser().getId())
                .ifPresent(user -> user.getMotorcycles().add(motorcycle));
    }

    /**
     * Creates new motorcycle for current caller principal.
     *
     * @param motorcycle new motorcycle
     */
    @RolesAllowed(UserRoles.USER)
    @LogMethodCall
    public void createForCallerPrincipal(Motorcycle motorcycle) {
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);

        motorcycle.setUser(user);
        create(motorcycle);
    }

    /**
     * Updates existing motorcycle in the data store.
     *
     * @param motorcycle motorcycle to be updated
     */
    @RolesAllowed(UserRoles.USER)
    @LogMethodCall
    public void update(Motorcycle motorcycle) {
        checkAdminRoleOrOwner(motorcycleRepository.find(motorcycle.getId()));
        motorcycleRepository.update(motorcycle);
    }

    /**
     * Deletes motorcycle from the data store.
     *
     * @param id motorcycle's id
     */
    @RolesAllowed(UserRoles.USER)
    @LogMethodCall
    public void delete(UUID id) {
        checkAdminRoleOrOwner(motorcycleRepository.find(id));
        motorcycleRepository.delete(motorcycleRepository.find(id).orElseThrow());
    }

    /**
     * @param motorcycle character to be checked
     * @throws EJBAccessException when caller principal has no admin role and is not character's owner
     */
    private void checkAdminRoleOrOwner(Optional<Motorcycle> motorcycle) throws EJBAccessException {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return;
        }
        if (securityContext.isCallerInRole(UserRoles.USER)
                && motorcycle.isPresent()
                && motorcycle.get().getUser().getLogin().equals(securityContext.getCallerPrincipal().getName())) {
            return;
        }
        throw new EJBAccessException("Caller not authorized.");
    }

}
