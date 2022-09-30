package com.anjunar.blomst.social.chat;

import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.*;
import com.anjunar.introspector.bean.BeanProperty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import java.util.Objects;
import java.util.UUID;

public class FromProvider extends AbstractRestPredicateProvider<UUID, User> {
    @Override
    public Predicate build(UUID value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<User> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (Objects.isNull(value)) {
            return builder.conjunction();
        }

        Subquery<UUID> subQuery = query.subquery(UUID.class);
        Root<UserConnection> subFrom = subQuery.from(UserConnection.class);
        subQuery.select(subFrom.get(UserConnection_.to).get(User_.id))
                .where(builder.equal(subFrom.get(UserConnection_.from).get(User_.id), value));

        return root.get(User_.id).in(subQuery);
    }
}
