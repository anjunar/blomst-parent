package com.anjunar.blomst.social.sites;

import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityProvider;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class SitesService extends AbstractCriteriaSearchService<Site, SitesSearch> {

    @Inject
    public SitesService(EntityManager entityManager, IdentityProvider identityProvider) {
        super(entityManager, identityProvider);
    }

    public SitesService() {
        this(null, null);
    }

}
