package com.anjunar.common.rest.api.jaxrs.provider;

import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.rest.api.jaxrs.AbstractRestPredicateProvider;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class GenericLikeNameProvider<E> extends AbstractRestPredicateProvider<String, E> {
    @Override
    public Predicate build(String value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<E> root, CriteriaQuery<?> query) {
        if (value != null && value.length() == 0) {
            return builder.conjunction();
        }
        return builder.like(builder.lower(root.get("name")), value.toLowerCase() + "%");
    }
}
