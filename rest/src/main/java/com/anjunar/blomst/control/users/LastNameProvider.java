package com.anjunar.blomst.control.users;

import com.anjunar.common.rest.search.AbstractVisibilityPredicateProvider;
import com.anjunar.common.security.*;
import com.anjunar.introspector.bean.BeanProperty;
import com.google.common.base.Strings;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

public class LastNameProvider extends AbstractVisibilityPredicateProvider<String, User> {
    @Override
    public Predicate build(String value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<User> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (Strings.isNullOrEmpty(value)) {
            return builder.conjunction();
        }

        SetJoin<User, UserRight> rightsJoin = root.join(User_.rights);
        SetJoin<UserRight, Category> categoryJoin = rightsJoin.join(UserRight_.categories);

        return builder.and(
                builder.like(builder.lower(root.get(User_.lastName)), value.toLowerCase() + "%"),
                builder.equal(rightsJoin.get(UserRight_.column), "lastName"),
                builder.or(
                        categoryJoin.get(Category_.id).in(generateUserConnectionCategory(builder, root, query)),
                        categoryJoin.get(Category_.id).in(generateEveryBodyCategory(builder, query))
                )
        );
    }

}
