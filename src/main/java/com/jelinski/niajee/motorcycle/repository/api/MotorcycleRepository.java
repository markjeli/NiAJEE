package com.jelinski.niajee.motorcycle.repository.api;

import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import com.jelinski.niajee.repository.api.Repository;
import com.jelinski.niajee.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Motorcycle entity. Repositories should be used in business layer (e.g.: in services).
 */
public interface MotorcycleRepository extends Repository<Motorcycle, UUID> {

    /**
     * Seeks for single user's motorcycle.
     *
     * @param id   character's id
     * @param user character's owner
     * @return container (can be empty) with motorcycle
     */
    Optional<Motorcycle> findByIdAndUser(UUID id, User user);

    /**
     * Seeks for all user's motorcycle.
     *
     * @param user characters' owner
     * @return list (can be empty) of user's motorcycles
     */
    List<Motorcycle> findAllByUser(User user);

    /**
     * Seeks for all motorcycles of given type.
     *
     * @param motorcycleType motorcycle's type
     * @return list (can be empty) of motorcycles
     */
    List<Motorcycle> findAllByMotorcycleType(MotorcycleType motorcycleType);
}
