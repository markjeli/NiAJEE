package com.jelinski.niajee.motorcycleType.repository.memory;


import com.jelinski.niajee.datastore.component.DataStore;
import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import com.jelinski.niajee.motorcycleType.repository.api.MotorcycleTypeRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for MotorcycleType entity. Repositories should be used in business layer (e.g.: in services).
 */
@RequestScoped
public class MotorcycleTypeInMemoryRepository implements MotorcycleTypeRepository {
    /**
     * Underlying data store. In future should be replaced with database connection.
     */
    private final DataStore store;

    /**
     * @param store data store
     */
    @Inject
    public MotorcycleTypeInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<MotorcycleType> find(UUID id) {
        return store.findAllMotorcycleTypes().stream()
                .filter(motorcycleType -> motorcycleType.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<MotorcycleType> findAll() {
        return store.findAllMotorcycleTypes();
    }

    @Override
    public void create(MotorcycleType entity) {
        store.createMotorcycleType(entity);
    }

    @Override
    public void delete(MotorcycleType entity) {
        store.deleteMotorcycleType(entity.getId());
    }

    @Override
    public void update(MotorcycleType entity) {
        store.updateMotorcycleType(entity);
    }
}
