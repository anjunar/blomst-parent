package com.anjunar.blomst.control.users.user.connections;

import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.UserConnection_;
import com.anjunar.common.security.User_;
import com.anjunar.common.security.UserConnection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.UUID;

public class ToProvider extends AbstractRestPredicateProvider<UUID, UserConnection> {
    @Override
    public Predicate build(UUID value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<UserConnection> root, CriteriaQuery<?> query) {
        if (value == null) {
            return builder.conjunction();
        }
        return builder.equal(root.get(UserConnection_.TO).get(User_.ID), value);
    }
}
