package com.anjunar.blomst.social.pages;

import com.google.common.base.Strings;
import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityProvider;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class WordProvider extends AbstractRestPredicateProvider<String, Page> {
    @Override
    public Predicate build(String value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<Page> root, CriteriaQuery<?> query) {
        if (Strings.isNullOrEmpty(value)) {
            return builder.conjunction();
        }
        return builder.gt(builder.function("contains", Integer.class, root.get(Page_.EDITOR).get(Editor_.TEXT), builder.literal(value), builder.literal(1)), 0);
    }
}
