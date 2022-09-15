package com.anjunar.common.rest.search.provider;

import com.anjunar.common.rest.api.DateDuration;
import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;

import com.anjunar.introspector.bean.BeanProperty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class GenericDurationDateProvider<E> extends AbstractRestPredicateProvider<DateDuration, E> {
    @Override
    public Predicate build(DateDuration value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<E> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (value != null && value.getFrom() != null && value.getTo() != null) {
            return builder.between(root.get(property.getKey()), value.getFrom(), value.getTo());
        }
        if (value != null && value.getFrom() != null && value.getTo() == null) {
            return builder.greaterThan(root.get(property.getKey()), value.getFrom());
        }
        if (value != null && value.getFrom() == null && value.getTo() != null) {
            return builder.lessThan(root.get(property.getKey()), value.getTo());
        }
        return builder.conjunction();
    }
}
