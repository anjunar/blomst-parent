package com.anjunar.blomst.social.pages;

import com.anjunar.blomst.shared.system.Language;
import com.anjunar.common.rest.api.jaxrs.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityProvider;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class LanguageProvider extends AbstractRestPredicateProvider<Language, Page> {
    @Override
    public Predicate build(Language value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<Page> root, CriteriaQuery<?> query) {
        if (value == null) {
            return builder.conjunction();
        }
        return builder.equal(root.get(Page_.language), value.getLocale());
    }
}
