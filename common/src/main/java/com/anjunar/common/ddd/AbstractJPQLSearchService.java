package com.anjunar.common.ddd;

import com.anjunar.common.rest.api.jaxrs.AbstractRestSearch;
import com.anjunar.common.security.IdentityProvider;
import com.google.common.reflect.TypeToken;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class AbstractJPQLSearchService<E, S extends AbstractRestSearch> {

    protected final EntityManager entityManager;

    protected final IdentityProvider identityProvider;

    public AbstractJPQLSearchService(EntityManager entityManager, IdentityProvider identityProvider) {
        this.entityManager = entityManager;
        this.identityProvider = identityProvider;
    }

    public Class<E> getEntityClass() {
        return (Class<E>) TypeToken.of(getClass()).resolveType(AbstractJPQLSearchService.class.getTypeParameters()[0]).getRawType();
    }

    public Class<S> getSearchClass() {
        return (Class<S>) TypeToken.of(getClass()).resolveType(AbstractJPQLSearchService.class.getTypeParameters()[1]).getRawType();
    }

    public Predicate filter(CriteriaBuilder builder, Root<E> root, CriteriaQuery<?> query) {
        return entityManager.getCriteriaBuilder().conjunction();
    }

    abstract public List<E> find(S search);

    abstract public long count(S search);

}
