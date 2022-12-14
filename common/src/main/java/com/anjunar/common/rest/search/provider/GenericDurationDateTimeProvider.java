package com.anjunar.common.rest.search.provider;

import com.anjunar.common.rest.api.DateTimeDuration;
import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.ddd.AbstractEntity;

import com.anjunar.introspector.bean.BeanProperty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDateTime;

public class GenericDurationDateTimeProvider extends AbstractRestPredicateProvider<DateTimeDuration, AbstractEntity> {
    @Override
    public Predicate build(DateTimeDuration value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<AbstractEntity> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (value == null || value.getFrom() == null || value.getTo() == null) {
            return builder.conjunction();
        }
        LocalDateTime start = value.getFrom();
        LocalDateTime end = value.getTo();
        return builder.between(root.get(property.getKey()), start, end);
    }
}
