package com.anjunar.common.ddd;

import com.anjunar.common.security.IdentityStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

@ApplicationScoped
public class AbstractEntityListener {

    private final Event<AbstractEntity> onPersist;

    private final IdentityStore identityStore;

    @Inject
    public AbstractEntityListener(@OnPersist Event<AbstractEntity> onPersist, IdentityStore identityStore) {
        this.onPersist = onPersist;
        this.identityStore = identityStore;
    }

    public AbstractEntityListener() {
        this(null, null);
    }

    @PostLoad
    private void onLoad(AbstractEntity entity) {
        entity.setIdentityStore(identityStore);
    }

    @PrePersist
    private void onPersist(AbstractEntity entity) {
        entity.setCreated(LocalDateTime.now());
        entity.setModified(LocalDateTime.now());
        onPersist.fire(entity);
    }

    @PreUpdate
    private void onMerge(AbstractEntity entity) {
        entity.setModified(LocalDateTime.now());
    }

}
