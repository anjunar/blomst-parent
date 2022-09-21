package com.anjunar.blomst.control.users.user.connections.categories;

import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.Category_;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.User_;
import com.anjunar.common.security.Category;

import com.anjunar.introspector.bean.BeanProperty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.UUID;

public class OwnerProvider extends AbstractRestPredicateProvider<UUID, Category> {
    @Override
    public Predicate build(UUID value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<Category> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (value == null) {
            return builder.conjunction();
        }
        return builder.or(builder.equal(root.get(Category_.OWNER).get(User_.ID), value), builder.isNull(root.get(Category_.OWNER)));
    }
}
