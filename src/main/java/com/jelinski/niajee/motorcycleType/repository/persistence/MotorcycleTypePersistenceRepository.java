package com.jelinski.niajee.motorcycleType.repository.persistence;

import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import com.jelinski.niajee.motorcycleType.repository.api.MotorcycleTypeRepository;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Motorcycle Type entity. Repositories should be used in business layer (e.g.: in services). The request
 * scope is a result of the fact that {@link EntityManager} objects cannot be used in multiple threads (are not thread
 * safe). Because services are CDI application scoped beans (technically singletons) then repositories must be thread
 * scoped in order to ensure single entity manager for single thread.
 */
@Dependent
public class MotorcycleTypePersistenceRepository implements MotorcycleTypeRepository {

    /**
     * Connection with the database (not thread safe).
     */
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<MotorcycleType> find(UUID id) {
        return Optional.ofNullable(em.find(MotorcycleType.class, id));
    }

    @Override
    public List<MotorcycleType> findAll() {
        return em.createQuery("select mt from MotorcycleType mt", MotorcycleType.class).getResultList();
    }

    @Override
    public void create(MotorcycleType entity) {
        em.persist(entity);
    }

    @Override
    public void delete(MotorcycleType entity) {
        em.remove(em.find(MotorcycleType.class, entity.getId()));
    }

    @Override
    public void update(MotorcycleType entity) {
        em.merge(entity);
    }
}
