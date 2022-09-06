package com.anjunar.blomst.shared.users;

import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.User;
import com.anjunar.common.security.User_;
import com.google.common.base.Strings;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

public class NamingProvider extends AbstractRestPredicateProvider<String, User> {
    @Override
    public Predicate build(String value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<User> root, CriteriaQuery<?> query) {
        if (Strings.isNullOrEmpty(value)) {
            return builder.conjunction();
        }

        Expression<String> concat = builder.concat(builder.concat(builder.lower(root.get(User_.firstName)), " "), builder.lower(root.get(User_.lastName)));
        return builder.or(
                builder.like(concat, value.toLowerCase() + "%"),
                builder.lessThan(builder.function("levensthein", Integer.class, concat, builder.literal(value.toLowerCase())), 4)
        );
    }
}
