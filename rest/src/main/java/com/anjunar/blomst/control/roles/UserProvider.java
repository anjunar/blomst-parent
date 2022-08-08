package com.anjunar.blomst.control.roles;

import com.anjunar.common.security.*;
import com.anjunar.common.rest.api.jaxrs.AbstractRestPredicateProvider;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import java.util.UUID;

public class UserProvider extends AbstractRestPredicateProvider<UUID, Role> {
    @Override
    public Predicate build(UUID value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<Role> root, CriteriaQuery<?> query) {
        if (value == null) {
            return builder.conjunction();
        }
        SetJoin<Role, User> join = root.join(Role_.users);
        Path<UUID> uuidPath = join.get(User_.id);

        return builder.equal(uuidPath, value);
    }
}
