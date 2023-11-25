package com.jelinski.niajee.motorcycle.repository.api;

import com.jelinski.niajee.motorcycle.entity.EnumMotorcycle;
import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import com.jelinski.niajee.repository.api.Repository;
import com.jelinski.niajee.user.entity.User;

import java.time.LocalDate;
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
     * Seeks for single user's motorcycle of given type.
     *
     * @param motorcycleType motorcycle's type
     * @param user           character's owner
     * @return container (can be empty) with motorcycle
     */
    List<Motorcycle> findAllByMotorcycleTypeAndUser(MotorcycleType motorcycleType, User user);

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

    /**
     * Seeks for all filtered motorcycles.
     *
     * @param name           motorcycle's name
     * @param horsepower     motorcycle's horsepower
     * @param color          motorcycle's color
     * @param brand          motorcycle's brand
     * @param productionDate motorcycle's production date
     * @param price          motorcycle's price
     * @param weight         motorcycle's weight
     * @param motorcycleType motorcycle's type
     * @param user           motorcycle's owner
     * @return list (can be empty) of motorcycles
     */
    List<Motorcycle> findAllFiltered(
            String name,
            int horsepower,
            EnumMotorcycle.Color color,
            EnumMotorcycle.Brand brand,
            LocalDate productionDate,
            int price,
            int weight,
            MotorcycleType motorcycleType,
            User user
            );

}
