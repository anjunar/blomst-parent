package com.anjunar.blomst.control.users.user.connections;

import com.anjunar.common.rest.api.jaxrs.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.control.users.Category_;
import com.anjunar.blomst.control.users.UserConnection;
import com.anjunar.blomst.control.users.UserConnection_;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.UUID;

public class CategoryProvider extends AbstractRestPredicateProvider<UUID, UserConnection> {
    @Override
    public Predicate build(UUID value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<UserConnection> root, CriteriaQuery<?> query) {
        if (value == null) {
            return builder.conjunction();
        }
        return builder.equal(root.get(UserConnection_.CATEGORY).get(Category_.ID), value);
    }
}