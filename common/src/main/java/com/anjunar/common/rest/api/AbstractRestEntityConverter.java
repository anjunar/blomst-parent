package com.anjunar.common.rest.api;

import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.ddd.AbstractEntity;

import javax.persistence.EntityManager;

public abstract class AbstractRestEntityConverter<E extends AbstractEntity, R extends AbstractRestEntity> {

    public R factory(R restEntity, E entity) {
        restEntity.setId(entity.getId());
        restEntity.setCreated(entity.getCreated());
        restEntity.setModified(entity.getModified());
        return restEntity;
    }

    public abstract E updater(R restEntity, E entity, EntityManager entityManager, IdentityProvider identityProvider);

}
