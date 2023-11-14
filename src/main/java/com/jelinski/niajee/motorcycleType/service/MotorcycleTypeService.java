package com.jelinski.niajee.motorcycleType.service;

import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import com.jelinski.niajee.motorcycleType.repository.api.MotorcycleTypeRepository;
import com.jelinski.niajee.user.entity.UserRoles;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for all business actions regarding motorcycleType entity.
 */
@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class MotorcycleTypeService {

    /**
     * Repository for motorcycleType entity.
     */
    private final MotorcycleTypeRepository repository;


    /**
     * @param repository repository for motorcycleType entity
     */
    @Inject
    public MotorcycleTypeService(MotorcycleTypeRepository repository) {
        this.repository = repository;
    }

    /**
     * @param id motorcycleType's id
     * @return container with motorcycleType entity
     */
    @RolesAllowed(UserRoles.USER)
    public Optional<MotorcycleType> find(UUID id) {
        return repository.find(id);
    }

    /**
     * @return all available motorcycleTypes
     */
    @RolesAllowed(UserRoles.USER)
    public List<MotorcycleType> findAll() {
        return repository.findAll();
    }

    /**
     * Stores new motorcycleType in the data store.
     *
     * @param motorcycleType new motorcycleType to be saved
     */
    @RolesAllowed(UserRoles.ADMIN)
    public void create(MotorcycleType motorcycleType) {
        repository.create(motorcycleType);
    }

    /**
     * Updates motorcycleType in the data store.
     *
     * @param motorcycleType motorcycleType to be updated
     */
    @RolesAllowed(UserRoles.ADMIN)
    public void update(MotorcycleType motorcycleType) {
        repository.update(motorcycleType);
    }

    /**
     * Deletes motorcycleType from the data store.
     *
     * @param id motorcycleType's id to be deleted
     */
    @RolesAllowed(UserRoles.ADMIN)
    public void delete(UUID id) {
        repository.delete(repository.find(id).orElseThrow());
    }

}
