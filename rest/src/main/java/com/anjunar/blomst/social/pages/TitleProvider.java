package com.anjunar.blomst.social.pages;

import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityProvider;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class TitleProvider extends AbstractRestPredicateProvider<String, Page> {
    @Override
    public Predicate build(String value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<Page> root, CriteriaQuery<?> query) {
        if (value == null || value.equals("")) {
            return builder.conjunction();
        }
        return builder.or(
                builder.like(builder.lower(root.get(Page_.title)), value.toLowerCase() + "%"),
                builder.lt(builder.function("levenshtein", Integer.class, root.get(Page_.title), builder.literal(value)), 3)
        );
    }
}
