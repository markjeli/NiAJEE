package com.jelinski.niajee.datastore.component;

import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import com.jelinski.niajee.motorcycleType.entity.MotorcycleType;
import com.jelinski.niajee.serialization.component.CloningUtility;
import com.jelinski.niajee.user.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * For the sake of simplification instead of using real database this example is using a data source object which should
 * be put in servlet context in a single instance. In order to avoid {@link java.util.ConcurrentModificationException}
 * all methods are synchronized. Normally synchronization would be carried on by the database server. Caution, this is
 * very inefficient implementation but can be used to present other mechanisms without obscuration example with ORM
 * usage.
 */
@Log
@ApplicationScoped
@NoArgsConstructor(force = true)
public class DataStore {

    /**
     * Set of all available motorcycle types.
     */
    private final Set<MotorcycleType> motorcycleTypes = new HashSet<>();

    /**
     * Set of all motorcycles.
     */
    private final Set<Motorcycle> motorcycles = new HashSet<>();

    /**
     * Set of all users.
     */
    private final Set<User> users = new HashSet<>();

    /**
     * Component used for creating deep copies.
     */
    private final CloningUtility cloningUtility;

    /**
     * @param cloningUtility component used for creating deep copies
     */
    @Inject
    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    /**
     * Seeks for all motorcycle types.
     *
     * @return list (can be empty) of all motorcycle types
     */
    public synchronized List<MotorcycleType> findAllMotorcycleTypes() {
        return motorcycleTypes.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    /**
     * Stores new motorcycle type.
     *
     * @param value new motorcycle type to be stored
     * @throws IllegalArgumentException if motorcycle type with provided id already exists
     */
    public synchronized void createMotorcycleType(MotorcycleType value) throws IllegalArgumentException {
        if (motorcycleTypes.stream().anyMatch(motorcycleType -> motorcycleType.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The motorcycle type id \"%s\" is not unique".formatted(value.getId()));
        }
        motorcycleTypes.add(cloningUtility.clone(value));
    }

    public synchronized void deleteMotorcycleType(UUID id) throws IllegalArgumentException {
        if (!motorcycleTypes.removeIf(motorcycleType -> motorcycleType.getId().equals(id))) {
            throw new IllegalArgumentException("The motorcycle type with id \"%s\" does not exist".formatted(id));
        }
    }

    /**
     * Seeks for all motorcycles.
     *
     * @return list (can be empty) of all motorcycles
     */
    public synchronized List<Motorcycle> findAllMotorcycles() {
        return motorcycles.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    /**
     * Stores new motorcycle.
     *
     * @param value new motorcycle to be stored
     * @throws IllegalArgumentException if motorcycle with provided id already exists or when {@link Motorcycle} or
     *                                  {@link MotorcycleType} with provided uuid does not exist
     */
    public synchronized void createMotorcycle(Motorcycle value) throws IllegalArgumentException {
        if (motorcycles.stream().anyMatch(motorcycle -> motorcycle.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The motorcycle id \"%s\" is not unique".formatted(value.getId()));
        }
        Motorcycle entity = cloneWithRelationships(value);
        motorcycles.add(entity);
    }

    /**
     * Updates existing motorcycle.
     *
     * @param value motorcycle to be updated
     * @throws IllegalArgumentException if motorcycle with the same id does not exist or when {@link Motorcycle} or
     *                                  {@link MotorcycleType} with provided uuid does not exist
     */
    public synchronized void updateMotorcycle(Motorcycle value) throws IllegalArgumentException {
        Motorcycle entity = cloneWithRelationships(value);
        if (motorcycles.removeIf(motorcycle -> motorcycle.getId().equals(value.getId()))) {
            motorcycles.add(entity);
        } else {
            throw new IllegalArgumentException("The motorcycle with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    /**
     * Deletes existing motorcycle.
     *
     * @param id id of motorcycle to be deleted
     * @throws IllegalArgumentException if motorcycle with provided id does not exist
     */
    public synchronized void deleteMotorcycle(UUID id) throws IllegalArgumentException {
        if (!motorcycles.removeIf(motorcycle -> motorcycle.getId().equals(id))) {
            throw new IllegalArgumentException("The motorcycle with id \"%s\" does not exist".formatted(id));
        }
    }

    /**
     * Seeks for all users.
     *
     * @return list (can be empty) of all users
     */
    public synchronized List<User> findAllUsers() {
        return users.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    /**
     * Stores new user.
     *
     * @param value new user to be stored
     * @throws IllegalArgumentException if user with provided id already exists
     */
    public synchronized void createUser(User value) throws IllegalArgumentException {
        if (users.stream().anyMatch(character -> character.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The user id \"%s\" is not unique".formatted(value.getId()));
        }
        users.add(cloningUtility.clone(value));
    }

    /**
     * Updates existing user.
     *
     * @param value user to be updated
     * @throws IllegalArgumentException if user with the same id does not exist
     */
    public synchronized void updateUser(User value) throws IllegalArgumentException {
        if (users.removeIf(character -> character.getId().equals(value.getId()))) {
            users.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The user with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    /**
     * Clones existing motorcycle and updates relationships for values in storage
     *
     * @param value motorcycle
     * @return cloned value with updated relationships
     * @throws IllegalArgumentException when {@link Motorcycle} or {@link MotorcycleType} with provided uuid does not exist
     */
    private Motorcycle cloneWithRelationships(Motorcycle value) {
        Motorcycle entity = cloningUtility.clone(value);

        if (entity.getUser() != null) {
            entity.setUser(users.stream()
                    .filter(user -> user.getId().equals(value.getUser().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No user with id \"%s\".".formatted(value.getUser().getId()))));
        }

        if (entity.getMotorcycleType() != null) {
            entity.setMotorcycleType(motorcycleTypes.stream()
                    .filter(motorcycleType -> motorcycleType.getId().equals(value.getMotorcycleType().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No motorcycle type with id \"%s\".".formatted(value.getMotorcycleType().getId()))));
        }

        return entity;
    }
}
