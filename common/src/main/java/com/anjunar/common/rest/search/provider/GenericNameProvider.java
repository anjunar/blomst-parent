package com.anjunar.common.rest.search.provider;

import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.introspector.bean.BeanProperty;
import com.google.common.base.Strings;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

public class GenericNameProvider<E> extends AbstractRestPredicateProvider<String, E> {
    @Override
    public Predicate build(String value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<E> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (Strings.isNullOrEmpty(value)) {
            return builder.conjunction();
        }

        Expression<Integer> levensthein = builder.function("levensthein", Integer.class, builder.lower(root.get(property.getKey())), builder.literal(value.toLowerCase()));
        Predicate like = builder.like(builder.lower(root.get(property.getKey())), value.toLowerCase() + "%");

        return builder.or(like, builder.lessThan(levensthein, 5));
    }
}
