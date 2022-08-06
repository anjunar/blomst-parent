package com.anjunar.blomst.control.users;

import com.anjunar.common.security.User_;
import com.anjunar.common.rest.api.jaxrs.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.User;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BirthDataProvider extends AbstractRestPredicateProvider<BirthDateForm, User> {
    @Override
    public Predicate build(BirthDateForm value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<User> root, CriteriaQuery<?> query) {
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
