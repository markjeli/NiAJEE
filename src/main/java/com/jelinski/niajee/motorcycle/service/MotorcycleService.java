package com.jelinski.niajee.motorcycle.service;

import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.motorcycle.repository.api.MotorcycleRepository;
import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import com.jelinski.niajee.motorcycleType.repository.api.MotorcycleTypeRepository;
import com.jelinski.niajee.user.repository.api.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
     * @return all available motorcycles by motorcycle type
     */
    public List<Motorcycle> findAll(MotorcycleType motorcycleType) {
        return motorcycleRepository.findAllByMotorcycleType(motorcycleType);
    }

    public Optional<List<Motorcycle>> findAllByMotorcycleType(UUID id) {
        return motorcycleTypeRepository.find(id)
                .map(motorcycleRepository::findAllByMotorcycleType);
    }

    /**
     * Stores new motorcycle in the data store.
     *
     * @param motorcycle new motorcycle to be saved
     */
    @Transactional
    public void create(Motorcycle motorcycle) {
        if (motorcycleRepository.find(motorcycle.getId()).isPresent()) {
            throw new IllegalArgumentException("Motorcycle already exists");
        }
        if (motorcycleTypeRepository.find(motorcycle.getMotorcycleType().getId()).isEmpty()) {
            throw new IllegalArgumentException("Motorcycle type does not exist");
        }
        motorcycleRepository.create(motorcycle);
    }

    /**
     * Updates existing motorcycle in the data store.
     *
     * @param motorcycle motorcycle to be updated
     */
    @Transactional
    public void update(Motorcycle motorcycle) {
        motorcycleRepository.update(motorcycle);
    }

    /**
     * Deletes motorcycle from the data store.
     *
     * @param id motorcycle's id
     */
    @Transactional
    public void delete(UUID id) {
        motorcycleRepository.delete(motorcycleRepository.find(id).orElseThrow());
    }

}
