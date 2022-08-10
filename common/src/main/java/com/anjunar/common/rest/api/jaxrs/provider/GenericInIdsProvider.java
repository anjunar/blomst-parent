package com.anjunar.common.rest.api.jaxrs.provider;

import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.ddd.AbstractEntity_;
import com.anjunar.common.rest.api.jaxrs.AbstractRestPredicateProvider;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.UUID;

public class GenericInIdsProvider<E extends AbstractEntity> extends AbstractRestPredicateProvider<List<UUID>, E> {
    @Override
    public Predicate build(List<UUID> value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<E> root, CriteriaQuery<?> query) {
        if (value == null) {
            return builder.disjunction();
        }
        if (value.isEmpty()) {
            return builder.conjunction();
        }
        return root.get(AbstractEntity_.id).in(value);
    }
}