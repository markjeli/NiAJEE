package com.jelinski.niajee.motorcycleType.service;

import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.motorcycle.repository.api.MotorcycleRepository;
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
    private final MotorcycleTypeRepository motorcycleTypeRepository;

    /**
     * Repository for motorcycle entity.
     */
    private final MotorcycleRepository motorcycleRepository;

    /**
     * @param motorcycleTypeRepository repository for motorcycleType entity
     * @param motorcycleRepository     repository for motorcycle entity
     */
    @Inject
    public MotorcycleTypeService(MotorcycleTypeRepository motorcycleTypeRepository, MotorcycleRepository motorcycleRepository) {
        this.motorcycleTypeRepository = motorcycleTypeRepository;
        this.motorcycleRepository = motorcycleRepository;
    }

    /**
     * @param id motorcycleType's id
     * @return container with motorcycleType entity
     */
    public Optional<MotorcycleType> find(UUID id) {
        return motorcycleTypeRepository.find(id);
    }

    /**
     * @return all available motorcycleTypes
     */
    public List<MotorcycleType> findAll() {
        return motorcycleTypeRepository.findAll();
    }

    /**
     * Stores new motorcycleType in the data store.
     *
     * @param motorcycleType new motorcycleType to be saved
     */
    public void create(MotorcycleType motorcycleType) {
        motorcycleTypeRepository.create(motorcycleType);
    }

    /**
     * Updates motorcycleType in the data store.
     *
     * @param motorcycleType motorcycleType to be updated
     */
    public void update(MotorcycleType motorcycleType) {
        motorcycleTypeRepository.update(motorcycleType);
    }

    /**
     * Deletes motorcycleType from the data store.
     *
     * @param id motorcycleType's id to be deleted
     */
    public void delete(UUID id) {
        MotorcycleType motorcycleType = motorcycleTypeRepository.find(id).orElseThrow();
        List<Motorcycle> motorcycles = motorcycleRepository.findAllByMotorcycleType(motorcycleType);
        if (!motorcycles.isEmpty()) {
            for (Motorcycle motorcycle : motorcycles) {
                motorcycleRepository.delete(motorcycle);
            }
        }
        motorcycleTypeRepository.delete(motorcycleType);
    }
}
