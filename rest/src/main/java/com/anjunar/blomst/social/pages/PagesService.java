package com.anjunar.blomst.social.pages;

import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityProvider;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class PagesService extends AbstractCriteriaSearchService<Page, PagesSearch> {

    @Inject
    public PagesService(EntityManager entityManager, IdentityProvider identityProvider) {
        super(entityManager, identityProvider);
    }

    public PagesService() {
        this(null, null);
    }

}
