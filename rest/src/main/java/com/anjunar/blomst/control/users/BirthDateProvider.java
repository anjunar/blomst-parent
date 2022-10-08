package com.anjunar.blomst.control.users;

import com.anjunar.common.rest.api.DateDuration;
import com.anjunar.common.rest.search.AbstractVisibilityPredicateProvider;
import com.anjunar.common.security.*;
import com.anjunar.introspector.bean.BeanProperty;
import com.google.common.base.Strings;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

public class BirthDateProvider extends AbstractVisibilityPredicateProvider<DateDuration, User> {
    @Override
    public Predicate build(DateDuration value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<User> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (value != null && value.getFrom() != null && value.getTo() != null) {
            SetJoin<User, UserRight> rightsJoin = root.join(User_.rights);
            SetJoin<UserRight, Category> categoryJoin = rightsJoin.join(UserRight_.categories);

            return builder.and(
                    builder.between(root.get(property.getKey()), value.getFrom(), value.getTo()),
                    builder.equal(rightsJoin.get(UserRight_.column), "birthDate"),
                    builder.or(
                            categoryJoin.get(Category_.id).in(generateUserConnectionCategory(builder, root, query)),
                            categoryJoin.get(Category_.id).in(generateEveryBodyCategory(builder, query))
                    )
            );

        }

        return builder.conjunction();

    }

}
