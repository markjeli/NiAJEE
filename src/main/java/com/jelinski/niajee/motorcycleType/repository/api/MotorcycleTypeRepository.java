package com.jelinski.niajee.motorcycleType.repository.api;

import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import com.jelinski.niajee.repository.api.Repository;

import java.util.UUID;

/**
 * Repository for MotorcycleType entity. Repositories should be used in business layer (e.g.: in services).
 */
public interface MotorcycleTypeRepository extends Repository<MotorcycleType, UUID> {

}
