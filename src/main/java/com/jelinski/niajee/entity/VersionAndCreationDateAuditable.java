package com.jelinski.niajee.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Base super class providing support for optimistic locking version and creation date.
 */
@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class VersionAndCreationDateAuditable {

    /**
     * Edit version fore optimistic locking.
     */
    @Version
    private Long version;

    /**
     * Creation date.
     */
    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    /**
     * Last update date.
     */
    @Column(name = "last_update_date_time")
    private LocalDateTime lastUpdateDateTime;

    /**
     * Update creation datetime.
     */
    @PrePersist
    public void updateCreationDateTime() {
        creationDateTime = LocalDateTime.now();
    }

    /**
     * Update last update datetime.
     */
    @PreUpdate
    public void updateLastUpdateDateTime() {
        lastUpdateDateTime = LocalDateTime.now();
    }

}
