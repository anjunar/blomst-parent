package com.anjunar.blomst.social.pages;

import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.Locale;

public class LanguageProvider extends AbstractRestPredicateProvider<Locale, Page> {
    @Override
    public Predicate build(Locale value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<Page> root, CriteriaQuery<?> query) {
        if (value == null) {
            return builder.conjunction();
        }
        return builder.equal(root.get(Page_.language), value);
    }
}
