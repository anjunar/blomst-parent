package com.anjunar.common.rest.search.provider;

import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.introspector.bean.BeanProperty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.Objects;
import java.util.UUID;

public class GenericManyToOneProvider<E> extends AbstractRestPredicateProvider<UUID, E> {
    @Override
    public Predicate build(UUID value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<E> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (Objects.nonNull(value)) {
            return builder.equal(root.get(property.getKey()).get("id"), value);
        }
        return builder.conjunction();
    }
}
