package com.jelinski.niajee.motorcycle.repository.api;

import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.repository.api.Repository;

import java.util.UUID;

/**
 * Repository for Motorcycle entity. Repositories should be used in business layer (e.g.: in services).
 */
public interface MotorcycleRepository extends Repository<Motorcycle, UUID> {
}
