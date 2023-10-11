package com.jelinski.niajee.motorcycleType.service;

import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import com.jelinski.niajee.motorcycleType.repository.api.MotorcycleTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for all business actions regarding motorcycleType entity.
 */
@ApplicationScoped
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
    public Optional<MotorcycleType> find(UUID id) {
        return repository.find(id);
    }

    /**
     * @return all available motorcycleTypes
     */
    public List<MotorcycleType> findAll() {
        return repository.findAll();
    }

    /**
     * Stores new motorcycleType in the data store.
     *
     * @param motorcycleType new motorcycleType to be saved
     */
    public void create(MotorcycleType motorcycleType) {
        repository.create(motorcycleType);
    }
}
