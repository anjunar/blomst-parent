package com.anjunar.blomst.control.users.user.connections;

import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.Category_;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.UserConnection;

import com.anjunar.common.security.UserConnection_;
import com.anjunar.introspector.bean.BeanProperty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.UUID;

public class CategoryProvider extends AbstractRestPredicateProvider<UUID, UserConnection> {
    @Override
    public Predicate build(UUID value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<UserConnection> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (value == null) {
            return builder.conjunction();
        }
        return builder.equal(root.get(UserConnection_.CATEGORY).get(Category_.ID), value);
    }
}
