package com.jelinski.niajee.motorcycle.repository.memory;

import com.jelinski.niajee.datastore.component.DataStore;
import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.motorcycle.repository.api.MotorcycleRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
