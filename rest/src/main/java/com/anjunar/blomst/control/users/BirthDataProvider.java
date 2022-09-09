package com.anjunar.blomst.control.users;

import com.anjunar.common.security.User_;
import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class BirthDataProvider extends AbstractRestPredicateProvider<BirthDateForm, User> {
    @Override
    public Predicate build(BirthDateForm value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<User> root, CriteriaQuery<?> query) {
        if (value != null && value.getStart() != null && value.getEnd() != null) {
            return builder.between(root.get(User_.birthDate), value.getStart(), value.getEnd());
        }
        if (value != null && value.getStart() != null && value.getEnd() == null) {
            return builder.greaterThan(root.get(User_.birthDate), value.getStart());
        }
        if (value != null && value.getStart() == null && value.getEnd() != null) {
            return builder.lessThan(root.get(User_.birthDate), value.getEnd());
        }
        return builder.conjunction();
    }
}
