package com.jelinski.niajee.user.repository.persistence;

import com.jelinski.niajee.user.entity.User;
import com.jelinski.niajee.user.entity.User_;
import com.jelinski.niajee.user.repository.api.UserRepository;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for User entity. Repositories should be used in business layer (e.g.: in services). The request scope is a
 * result of the fact that {@link EntityManager} objects cannot be used in multiple threads (are not thread safe).
 * Because services are CDI application scoped beans (technically singletons) then repositories must be thread scoped in
 * order to ensure single entity manager for single thread.
 */
@Dependent
public class UserPersistenceRepository implements UserRepository {

    /**
     * Connection with the database (not thread safe).
     */
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<User> find(UUID id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void create(User entity) {
        em.persist(entity);
    }

    @Override
    public void delete(User entity) {
        em.remove(em.find(User.class, entity.getId()));
    }

    @Override
    public void update(User entity) {
        em.merge(entity);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root)
                    .where(cb.equal(root.get(User_.login), login));

            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
