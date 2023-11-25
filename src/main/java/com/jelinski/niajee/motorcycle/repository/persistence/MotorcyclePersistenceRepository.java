package com.jelinski.niajee.motorcycle.repository.persistence;

import com.jelinski.niajee.motorcycle.entity.EnumMotorcycle;
import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.motorcycle.entity.Motorcycle_;
import com.jelinski.niajee.motorcycle.repository.api.MotorcycleRepository;
import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import com.jelinski.niajee.user.entity.User;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Motorcycle entity. Repositories should be used in business layer (e.g.: in services). The request scope
 * is a result of the fact that {@link EntityManager} objects cannot be used in multiple threads (are not thread safe).
 * Because services are CDI application scoped beans (technically singletons) then repositories must be thread scoped in
 * order to ensure single entity manager for single thread.
 */
@Dependent
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
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Motorcycle> query = cb.createQuery(Motorcycle.class);
        Root<Motorcycle> root = query.from(Motorcycle.class);
        query.select(root)
                .where(cb.equal(root.get(Motorcycle_.motorcycleType), motorcycleType));
        return em.createQuery(query).getResultList();
    }

    @Override
    public Optional<Motorcycle> findByIdAndUser(UUID id, User user) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Motorcycle> query = cb.createQuery(Motorcycle.class);
            Root<Motorcycle> root = query.from(Motorcycle.class);
            query.select(root)
                    .where(cb.and(
                            cb.equal(root.get(Motorcycle_.id), id),
                            cb.equal(root.get(Motorcycle_.user), user)
                    ));
            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Motorcycle> findAllByMotorcycleTypeAndUser(MotorcycleType motorcycleType, User user) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Motorcycle> query = cb.createQuery(Motorcycle.class);
        Root<Motorcycle> root = query.from(Motorcycle.class);
        query.select(root)
                .where(cb.and(
                        cb.equal(root.get(Motorcycle_.motorcycleType), motorcycleType),
                        cb.equal(root.get(Motorcycle_.user), user)
                ));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Motorcycle> findAllByUser(User user) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Motorcycle> query = cb.createQuery(Motorcycle.class);
        Root<Motorcycle> root = query.from(Motorcycle.class);
        query.select(root)
                .where(cb.equal(root.get(Motorcycle_.user), user));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Motorcycle> findAllFiltered(
            String name,
            int horsepower,
            EnumMotorcycle.Color color,
            EnumMotorcycle.Brand brand,
            LocalDate productionDate,
            int price,
            int weight,
            MotorcycleType motorcycleType,
            User user
    ) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Motorcycle> query = cb.createQuery(Motorcycle.class);
        Root<Motorcycle> root = query.from(Motorcycle.class);
        query.select(root)
                .where(cb.and(
                        cb.equal(root.get(Motorcycle_.name), name),
                        cb.equal(root.get(Motorcycle_.horsepower), horsepower),
                        cb.equal(root.get(Motorcycle_.color), color),
                        cb.equal(root.get(Motorcycle_.brand), brand),
                        cb.equal(root.get(Motorcycle_.productionDate), productionDate),
                        cb.equal(root.get(Motorcycle_.price), price),
                        cb.equal(root.get(Motorcycle_.weight), weight),
                        cb.equal(root.get(Motorcycle_.motorcycleType), motorcycleType),
                        cb.equal(root.get(Motorcycle_.user), user)
                ));
        return em.createQuery(query).getResultList();
    }

    @Override
    public Optional<Motorcycle> find(UUID id) {
        return Optional.ofNullable(em.find(Motorcycle.class, id));
    }

    @Override
    public List<Motorcycle> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Motorcycle> query = cb.createQuery(Motorcycle.class);
        Root<Motorcycle> root = query.from(Motorcycle.class);
        query.select(root);
        return em.createQuery(query).getResultList();
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
