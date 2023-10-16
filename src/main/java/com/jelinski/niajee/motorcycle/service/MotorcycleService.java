package com.jelinski.niajee.motorcycle.service;

import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.motorcycle.repository.api.MotorcycleRepository;
import com.jelinski.niajee.motorcycleType.repository.api.MotorcycleTypeRepository;
import com.jelinski.niajee.user.repository.api.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for all business actions regarding motorcycle entity.
 */
@ApplicationScoped
@NoArgsConstructor(force = true)
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
     * @param motorcycleRepository     repository for motorcycle entity
     * @param motorcycleTypeRepository repository for motorcycleType entity
     * @param userRepository           repository for user entity
     */
    @Inject
    public MotorcycleService(MotorcycleRepository motorcycleRepository, MotorcycleTypeRepository motorcycleTypeRepository, UserRepository userRepository) {
        this.motorcycleRepository = motorcycleRepository;
        this.motorcycleTypeRepository = motorcycleTypeRepository;
        this.userRepository = userRepository;
    }

    /**
     * Finds single motorcycle.
     *
     * @param id motorcycle's id
     * @return container with motorcycle
     */
    public Optional<Motorcycle> find(UUID id) {
        return motorcycleRepository.find(id);
    }

    /**
     * @return all available motorcycles
     */
    public List<Motorcycle> findAll() {
        return motorcycleRepository.findAll();
    }

    /**
     * Stores new motorcycle in the data store.
     *
     * @param motorcycle new motorcycle to be saved
     */
    public void create(Motorcycle motorcycle) {
        motorcycleRepository.create(motorcycle);
    }

    /**
     * Deletes motorcycle from the data store.
     *
     * @param id motorcycle's id
     */
    public void delete(UUID id) {
        motorcycleRepository.delete(motorcycleRepository.find(id).orElseThrow());
    }

}
