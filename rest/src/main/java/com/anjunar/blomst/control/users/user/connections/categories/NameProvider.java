package com.anjunar.blomst.control.users.user.connections.categories;

import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.Category_;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.Category;
import org.apache.commons.lang3.StringUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class NameProvider extends AbstractRestPredicateProvider<String, Category> {
    @Override
    public Predicate build(String value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<Category> root, CriteriaQuery<?> query) {
        if (StringUtils.isAllEmpty(value)) {
            return builder.conjunction();
        }
        return builder.like(builder.lower(root.get(Category_.NAME)), value.toLowerCase() + "%");
    }
}
