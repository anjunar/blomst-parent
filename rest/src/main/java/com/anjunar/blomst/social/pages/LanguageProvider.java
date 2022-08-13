package com.anjunar.blomst.social.pages;

import com.anjunar.blomst.shared.system.Language;
import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityProvider;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class LanguageProvider extends AbstractRestPredicateProvider<Language, Page> {
    @Override
    public Predicate build(Language value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<Page> root, CriteriaQuery<?> query) {
        if (value == null) {
            return builder.conjunction();
        }
        return builder.equal(root.get(Page_.language), value.getLocale());
    }
}
