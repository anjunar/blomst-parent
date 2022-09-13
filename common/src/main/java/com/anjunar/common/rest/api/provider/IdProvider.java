package com.anjunar.common.rest.api.provider;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.ddd.AbstractEntity_;
import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;
import com.google.common.base.Strings;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class IdProvider  extends AbstractRestPredicateProvider<String, AbstractEntity> {
    @Override
    public Predicate build(String value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<AbstractEntity> root, CriteriaQuery<?> query) {
        if (Strings.isNullOrEmpty(value)) {
            return builder.conjunction();
        }
        return builder.like(builder.lower(root.get(AbstractEntity_.id).as(String.class)), builder.literal(value.toLowerCase() + "%"));
    }
}
