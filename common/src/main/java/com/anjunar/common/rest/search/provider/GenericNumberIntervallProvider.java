package com.anjunar.common.rest.search.provider;

import com.anjunar.common.rest.api.LongIntervall;
import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.introspector.bean.BeanProperty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import java.util.Objects;

public class GenericNumberIntervallProvider<E> extends AbstractRestPredicateProvider<LongIntervall, E> {
    @Override
    public Predicate build(LongIntervall value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<E> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (Objects.nonNull(value)) {
            Path<Long> path = root.get(property.getKey());
            return builder.between(path, value.getFrom(), value.getTo());
        }
        return null;
    }
}
