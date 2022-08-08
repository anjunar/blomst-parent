package com.anjunar.common.ddd;

import com.anjunar.common.rest.api.jaxrs.AbstractRestSearch;
import com.anjunar.common.rest.api.jaxrs.RestSearch;
import com.anjunar.common.security.IdentityProvider;
import com.google.common.reflect.TypeToken;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;

public abstract class AbstractCriteriaSearchService<E, S extends AbstractRestSearch> {

    protected final EntityManager entityManager;

    protected final IdentityProvider identityProvider;

    public AbstractCriteriaSearchService(EntityManager entityManager, IdentityProvider identityProvider) {
        this.entityManager = entityManager;
        this.identityProvider = identityProvider;
    }

    public Class<E> getEntityClass() {
        return (Class<E>) TypeToken.of(getClass()).resolveType(AbstractCriteriaSearchService.class.getTypeParameters()[0]).getRawType();
    }

    public Class<S> getSearchClass() {
        return (Class<S>) TypeToken.of(getClass()).resolveType(AbstractCriteriaSearchService.class.getTypeParameters()[1]).getRawType();
    }

    public IdentityProvider identity() {
        return identityProvider;
    }

    public Predicate filter(CriteriaBuilder builder, Root<E> root, CriteriaQuery<?> query) {
        return entityManager.getCriteriaBuilder().conjunction();
    }

    public List<E> find(S search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> query = builder.createQuery(getEntityClass());
        Root<E> root = query.from(getEntityClass());
        query.select(root)
                .where(builder.and(
                        filter(builder, root, query),
                        RestSearch.predicate(getSearchClass(), search, identityProvider, entityManager, builder, root, query)
                ))
                .orderBy(RestSearch.sort(getSearchClass(), search, entityManager, builder, root));
        TypedQuery<E> typedQuery = entityManager.createQuery(query);
        if (search.getIndex() == null) {
            typedQuery.setFirstResult(0);
        } else {
            typedQuery.setFirstResult(search.getIndex());
        }
        if (search.getLimit() == null) {
            typedQuery.setMaxResults(0);
        } else {
            typedQuery.setMaxResults(search.getLimit());
        }
        return typedQuery.getResultList();
    }

    public long count(S search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<E> root = query.from(getEntityClass());
        query.select(builder.count(root))
                .where(builder.and(
                        filter(builder, root, query),
                        RestSearch.predicate(getSearchClass(), search, identityProvider, entityManager, builder, root, query)
                ));
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        return typedQuery.getSingleResult();
    }

}
