package com.anjunar.blomst.social.sites.site.connections;

import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.social.sites.SiteConnection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class SiteConnectionService extends AbstractCriteriaSearchService<SiteConnection, SiteConnectionsSearch> {

    @Inject
    public SiteConnectionService(EntityManager entityManager, IdentityProvider identityProvider) {
        super(entityManager, identityProvider);
    }

    public SiteConnectionService() {
        this(null, null);
    }

}
