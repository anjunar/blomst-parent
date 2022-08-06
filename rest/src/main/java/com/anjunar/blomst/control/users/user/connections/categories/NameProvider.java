package com.anjunar.blomst.control.users.user.connections.categories;

import com.anjunar.common.rest.api.jaxrs.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.control.users.Category;
import com.anjunar.blomst.control.users.Category_;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class NameProvider extends AbstractRestPredicateProvider<String, Category> {
    @Override
    public Predicate build(String value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<Category> root, CriteriaQuery<?> query) {
        if (StringUtils.isAllEmpty(value)) {
            return builder.conjunction();
        }
        return builder.like(builder.lower(root.get(Category_.NAME)), value.toLowerCase() + "%");
    }
}
