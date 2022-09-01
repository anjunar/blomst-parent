package com.anjunar.blomst.control.users.user.connections.categories;

import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.Category_;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.User_;
import com.anjunar.common.security.Category;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.UUID;

public class OwnerProvider extends AbstractRestPredicateProvider<UUID, Category> {
    @Override
    public Predicate build(UUID value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<Category> root, CriteriaQuery<?> query) {
        if (value == null) {
            return builder.conjunction();
        }
        return builder.equal(root.get(Category_.OWNER).get(User_.ID), value);
    }
}
