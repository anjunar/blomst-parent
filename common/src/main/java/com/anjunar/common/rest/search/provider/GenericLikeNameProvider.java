package com.anjunar.common.rest.search.provider;

import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class GenericLikeNameProvider<E> extends AbstractRestPredicateProvider<String, E> {
    @Override
    public Predicate build(String value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<E> root, CriteriaQuery<?> query) {
        if (value != null && value.length() == 0) {
            return builder.conjunction();
        }
        return builder.like(builder.lower(root.get("name")), value.toLowerCase() + "%");
    }
}
