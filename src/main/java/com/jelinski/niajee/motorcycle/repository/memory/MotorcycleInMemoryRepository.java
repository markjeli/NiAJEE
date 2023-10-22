package com.jelinski.niajee.motorcycle.repository.memory;

import com.jelinski.niajee.datastore.component.DataStore;
import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.motorcycle.repository.api.MotorcycleRepository;
import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Repository for Motorcycle entity. Repositories should be used in business layer (e.g.: in services).
 */
@RequestScoped
public class MotorcycleInMemoryRepository implements MotorcycleRepository {
    /**
     * Underlying data store. In future should be replaced with database connection.
     */
    private final DataStore store;

    /**
     * @param store data store
     */
    @Inject
    public MotorcycleInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public List<Motorcycle> findAll() {
        return store.findAllMotorcycles();
    }

    @Override
    public List<Motorcycle> findAllByMotorcycleType(MotorcycleType motorcycleType) {
        return store.findAllMotorcycles().stream()
                .filter(motorcycle -> motorcycleType.equals(motorcycle.getMotorcycleType()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Motorcycle> find(UUID id) {
        return store.findAllMotorcycles().stream()
                .filter(motorcycle -> motorcycle.getId().equals(id))
                .findFirst();
    }

    @Override
    public void create(Motorcycle entity) {
        store.createMotorcycle(entity);
    }

    @Override
    public void delete(Motorcycle entity) {
        store.deleteMotorcycle(entity.getId());
    }

    @Override
    public void update(Motorcycle entity) {
        store.updateMotorcycle(entity);
    }

}
