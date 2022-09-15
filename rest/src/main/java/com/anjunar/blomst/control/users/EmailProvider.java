package com.anjunar.blomst.control.users;

import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.*;
import com.anjunar.introspector.bean.BeanProperty;
import com.google.common.base.Strings;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

public class EmailProvider extends AbstractRestPredicateProvider<String, User> {
    @Override
    public Predicate build(String value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<User> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (Strings.isNullOrEmpty(value)) {
            return builder.conjunction();
        }
        ListJoin<User, EmailType> join = root.join(User_.emails);
        return builder.like(builder.lower(join.get(EmailType_.value)), value.toLowerCase() + "%");
    }
}
