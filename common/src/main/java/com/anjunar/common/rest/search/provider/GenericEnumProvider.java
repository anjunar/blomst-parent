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

public class GenericEnumProvider<E> extends AbstractRestPredicateProvider<Enum<?>, E> {
    @Override
    public Predicate build(Enum<?> value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<E> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (Objects.nonNull(value)) {
            return builder.equal(root.get(property.getKey()), value);
        }
        return builder.conjunction();
    }
}
