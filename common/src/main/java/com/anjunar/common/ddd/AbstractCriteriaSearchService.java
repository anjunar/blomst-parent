package com.anjunar.common.ddd;

import com.anjunar.common.rest.search.AbstractRestSearch;
import com.anjunar.common.rest.search.RestSearch;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.introspector.bean.BeanIntrospector;
import com.anjunar.introspector.bean.BeanModel;
import com.anjunar.introspector.bean.BeanProperty;
import com.google.common.reflect.TypeToken;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Objects;

public abstract class AbstractCriteriaSearchService<E, S extends AbstractRestSearch> {

    protected final EntityManager entityManager;

    protected final IdentityManager identityManager;

    public AbstractCriteriaSearchService(EntityManager entityManager, IdentityManager identityManager) {
        this.entityManager = entityManager;
        this.identityManager = identityManager;
    }

    public Class<E> getEntityClass() {
        return (Class<E>) TypeToken.of(getClass()).resolveType(AbstractCriteriaSearchService.class.getTypeParameters()[0]).getRawType();
    }

    public Class<S> getSearchClass() {
        return (Class<S>) TypeToken.of(getClass()).resolveType(AbstractCriteriaSearchService.class.getTypeParameters()[1]).getRawType();
    }

    public IdentityManager identity() {
        return identityManager;
    }

    public Predicate filter(CriteriaBuilder builder, Root<E> root, CriteriaQuery<?> query) {
        return entityManager.getCriteriaBuilder().conjunction();
    }

    public List<E> find(S search) {
        return find(search, null);
    }

    public List<E> find(S search, Class<?> projectionClass) {
        EntityGraph<E> entityGraph;
        if (Objects.nonNull(projectionClass)) {
            entityGraph = entityManager.createEntityGraph(getEntityClass());
            BeanModel<?> beanModel = BeanIntrospector.create(projectionClass);
            for (BeanProperty<?, ?> property : beanModel.getProperties()) {
                entityGraph.addAttributeNodes(property.getKey());
            }
        }  else {
            entityGraph = null;
        }

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> query = builder.createQuery(getEntityClass());
        Root<E> root = query.from(getEntityClass());
        query.select(root)
                .where(builder.and(
                        filter(builder, root, query),
                        RestSearch.predicate(getSearchClass(), search, identityManager, entityManager, builder, root, query)
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

        if (Objects.nonNull(entityGraph)) {
            typedQuery.setHint("jakarta.persistence.loadgraph", entityGraph);
            return typedQuery.getResultList();
        } else {
            return typedQuery.getResultList();
        }


    }

    public long count(S search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<E> root = query.from(getEntityClass());
        query.select(builder.count(root))
                .where(builder.and(
                        filter(builder, root, query),
                        RestSearch.predicate(getSearchClass(), search, identityManager, entityManager, builder, root, query)
                ));
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        return typedQuery.getSingleResult();
    }

}
