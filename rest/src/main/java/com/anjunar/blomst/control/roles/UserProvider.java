package com.anjunar.blomst.control.roles;

import com.anjunar.common.security.*;
import com.anjunar.common.rest.search.AbstractRestPredicateProvider;

import com.anjunar.introspector.bean.BeanProperty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import java.util.UUID;

public class UserProvider extends AbstractRestPredicateProvider<UUID, Role> {
    @Override
    public Predicate build(UUID value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<Role> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (value == null) {
            return builder.conjunction();
        }
        SetJoin<Role, User> join = root.join(Role_.users);
        Path<UUID> uuidPath = join.get(User_.id);

        return builder.equal(uuidPath, value);
    }
}
