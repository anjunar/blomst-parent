package com.anjunar.common.rest.search.provider;

import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.*;
import com.anjunar.introspector.bean.BeanProperty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import java.util.Set;
import java.util.UUID;

public class GenericOneToManyProvider<E> extends AbstractRestPredicateProvider<Set<UUID>, E> {
    @Override
    public Predicate build(Set<UUID> value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<E> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (value != null && value.isEmpty()) {
            return builder.conjunction();
        }
        Join<Object, Object> join = root.join(property.getKey());
        return join.get("id").in(value);
    }
}
