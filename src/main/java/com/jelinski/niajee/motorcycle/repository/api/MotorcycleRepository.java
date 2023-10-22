package com.jelinski.niajee.motorcycle.repository.api;

import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import com.jelinski.niajee.repository.api.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for Motorcycle entity. Repositories should be used in business layer (e.g.: in services).
 */
public interface MotorcycleRepository extends Repository<Motorcycle, UUID> {

    /**
     * Seeks for all motorcycles of given type.
     *
     * @param motorcycleType motorcycle's type
     * @return list (can be empty) of motorcycles
     */
    List<Motorcycle> findAllByMotorcycleType(MotorcycleType motorcycleType);
}
