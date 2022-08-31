package com.anjunar.common.ddd;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.persistence.PrePersist;

@ApplicationScoped
public class AbstractEntityListener {

    private final Event<AbstractEntity> onPersist;

    @Inject
    public AbstractEntityListener(@OnPersist Event<AbstractEntity> onPersist) {
        this.onPersist = onPersist;
    }

    public AbstractEntityListener() {
        this(null);
    }

    @PrePersist
    private void onPersist(AbstractEntity entity) {
        onPersist.fire(entity);
    }

}
