package com.anjunar.common.ddd;

import com.anjunar.common.security.IdentityProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;

@ApplicationScoped
public class AbstractEntityListener {

    private final Event<AbstractEntity> onPersist;

    private final IdentityProvider identityProvider;

    @Inject
    public AbstractEntityListener(@OnPersist Event<AbstractEntity> onPersist, IdentityProvider identityProvider) {
        this.onPersist = onPersist;
        this.identityProvider = identityProvider;
    }

    public AbstractEntityListener() {
        this(null, null);
    }

    @PostLoad
    private void onLoad(AbstractEntity entity) {
        entity.setIdentityProvider(identityProvider);
    }

    @PrePersist
    private void onPersist(AbstractEntity entity) {
        onPersist.fire(entity);
    }

}
