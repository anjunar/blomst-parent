package com.anjunar.blomst.control.users;

import com.anjunar.common.rest.api.DateDuration;
import com.anjunar.common.security.User_;
import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class BirthDateProvider extends AbstractRestPredicateProvider<DateDuration, User> {
    @Override
    public Predicate build(DateDuration value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<User> root, CriteriaQuery<?> query) {
        if (value != null && value.getFrom() != null && value.getTo() != null) {
            return builder.between(root.get(User_.birthDate), value.getFrom(), value.getTo());
        }
        if (value != null && value.getFrom() != null && value.getTo() == null) {
            return builder.greaterThan(root.get(User_.birthDate), value.getFrom());
        }
        if (value != null && value.getFrom() == null && value.getTo() != null) {
            return builder.lessThan(root.get(User_.birthDate), value.getTo());
        }
        return builder.conjunction();
    }
}
