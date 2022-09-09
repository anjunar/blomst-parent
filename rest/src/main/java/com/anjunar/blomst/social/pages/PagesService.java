package com.anjunar.blomst.social.pages;

import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityManager;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

public class PagesService extends AbstractCriteriaSearchService<Page, PagesSearch> {

    @Inject
    public PagesService(EntityManager entityManager, IdentityManager identityManager) {
        super(entityManager, identityManager);
    }

    public PagesService() {
        this(null, null);
    }

}
