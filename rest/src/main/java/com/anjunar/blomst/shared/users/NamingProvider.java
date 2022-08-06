package com.anjunar.blomst.shared.users;

import com.google.common.base.Strings;
import com.anjunar.common.rest.api.jaxrs.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.User;
import com.anjunar.common.security.User_;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class NamingProvider extends AbstractRestPredicateProvider<String, User> {
    @Override
    public Predicate build(String value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<User> root, CriteriaQuery<?> query) {
        if (Strings.isNullOrEmpty(value)) {
            return builder.conjunction();
        }
        return builder.like(builder.concat(builder.concat(builder.lower(root.get(User_.firstName)), " "), builder.lower(root.get(User_.lastName))), value.toLowerCase() + "%");
    }
}
