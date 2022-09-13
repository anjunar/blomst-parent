package com.anjunar.blomst.control.users;

import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import java.util.Set;
import java.util.UUID;

public class RolesProvider extends AbstractRestPredicateProvider<Set<UUID>, User> {
    @Override
    public Predicate build(Set<UUID> value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<User> root, CriteriaQuery<?> query) {
        if (value != null && value.isEmpty()) {
            return builder.conjunction();
        }
        SetJoin<User, Role> join = root.join(User_.roles);
        return join.get(Role_.id).in(value);
    }
}
