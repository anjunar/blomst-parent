package com.anjunar.common.rest.api;

import com.anjunar.common.ddd.AbstractEntity_;
import com.anjunar.common.rest.api.jaxrs.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.ddd.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

public class DurationCreatedProvider extends AbstractRestPredicateProvider<DateTimeDuration, AbstractEntity> {
    @Override
    public Predicate build(DateTimeDuration value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<AbstractEntity> root, CriteriaQuery<?> query) {
        if (value == null || value.getFrom() == null || value.getTo() == null) {
            return builder.conjunction();
        }
        LocalDateTime start = value.getFrom();
        LocalDateTime end = value.getTo();
        return builder.between(root.get(AbstractEntity_.created), start, end);
    }
}
