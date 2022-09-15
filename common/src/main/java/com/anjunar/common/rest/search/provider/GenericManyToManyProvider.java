package com.anjunar.common.rest.search.provider;

import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.introspector.bean.BeanProperty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.Set;
import java.util.UUID;

public class GenericManyToManyProvider<E> extends AbstractRestPredicateProvider<Set<UUID>, E> {
    @Override
    public Predicate build(Set<UUID> value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<E> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (value != null && value.size() > 0) {
            return root.join(property.getKey()).get("id").in(value);
        }
        return builder.conjunction();
    }
}
