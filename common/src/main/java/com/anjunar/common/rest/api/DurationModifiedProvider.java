package com.anjunar.common.rest.api;

import com.anjunar.common.ddd.AbstractEntity_;
import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.ddd.AbstractEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDateTime;

public class DurationModifiedProvider extends AbstractRestPredicateProvider<DateTimeDuration, AbstractEntity> {
    @Override
    public Predicate build(DateTimeDuration value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<AbstractEntity> root, CriteriaQuery<?> query) {
        if (value == null || value.getFrom() == null || value.getTo() == null) {
            return builder.conjunction();
        }
        LocalDateTime start = value.getFrom();
        LocalDateTime end = value.getTo();
        return builder.between(root.get(AbstractEntity_.modified), start, end);
    }
}
