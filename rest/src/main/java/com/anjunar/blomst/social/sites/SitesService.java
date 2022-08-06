package com.anjunar.blomst.social.sites;

import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

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
