package com.jelinski.niajee.motorcycle.repository.persistence;

import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import com.jelinski.niajee.motorcycle.repository.api.MotorcycleRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Motorcycle entity. Repositories should be used in business layer (e.g.: in services). The request scope
 * is a result of the fact that {@link EntityManager} objects cannot be used in multiple threads (are not thread safe).
 * Because services are CDI application scoped beans (technically singletons) then repositories must be thread scoped in
 * order to ensure single entity manager for single thread.
 */
@RequestScoped
public class MotorcyclePersistenceRepository implements MotorcycleRepository {

    /**
     * Connection with the database (not thread safe).
     */
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Motorcycle> findAllByMotorcycleType(MotorcycleType motorcycleType) {
        return em.createQuery("select m from Motorcycle m where m.motorcycleType = :motorcycleType", Motorcycle.class)
                .setParameter("motorcycleType", motorcycleType)
                .getResultList();
    }

    @Override
    public Optional<Motorcycle> find(UUID id) {
        return Optional.ofNullable(em.find(Motorcycle.class, id));
    }

    @Override
    public List<Motorcycle> findAll() {
        return em.createQuery("select m from Motorcycle m", Motorcycle.class).getResultList();
    }

    @Override
    public void create(Motorcycle entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Motorcycle entity) {
        em.remove(em.find(Motorcycle.class, entity.getId()));
    }

    @Override
    public void update(Motorcycle entity) {
        em.merge(entity);
    }
}
