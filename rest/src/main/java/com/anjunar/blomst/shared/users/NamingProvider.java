package com.anjunar.blomst.shared.users;

import com.google.common.base.Strings;
import com.anjunar.common.rest.api.jaxrs.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.User;
import com.anjunar.common.security.User_;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class NamingProvider extends AbstractRestPredicateProvider<String, User> {
    @Override
    public Predicate build(String value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<User> root, CriteriaQuery<?> query) {
        if (Strings.isNullOrEmpty(value)) {
            return builder.conjunction();
        }
        return builder.like(builder.concat(builder.concat(builder.lower(root.get(User_.firstName)), " "), builder.lower(root.get(User_.lastName))), value.toLowerCase() + "%");
    }
}
