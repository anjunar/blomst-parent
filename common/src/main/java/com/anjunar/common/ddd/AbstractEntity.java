package com.anjunar.common.ddd;

import com.anjunar.common.security.IdentityManager;
import org.hibernate.annotations.FilterDef;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Patrick Bittner on 31/01/15.
 */
@FilterDef(name = "deletedFilter")
@MappedSuperclass
@EntityListeners(AbstractEntityListener.class)
public abstract class AbstractEntity implements Entity {

    @Id
    @Column(name = "id", length = 16, unique = true, nullable = false)
    private UUID id = UUID.randomUUID();

    @Version
    private int version;

    private LocalDateTime created;

    private LocalDateTime modified;

    private boolean deleted;
    @Transient
    private IdentityManager identityManager;

    @PreUpdate
    private void postUpdate() {
        modified = LocalDateTime.now();
    }

    @PrePersist
    private void postCreate() {
        modified = LocalDateTime.now();
        created = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntity)) return false;

        AbstractEntity that = (AbstractEntity) o;

        if (!Objects.equals(id, that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public void setIdentityProvider(IdentityManager identityManager) {
        this.identityManager = identityManager;
    }

    public IdentityManager getIdentityProvider() {
        return identityManager;
    }
}
