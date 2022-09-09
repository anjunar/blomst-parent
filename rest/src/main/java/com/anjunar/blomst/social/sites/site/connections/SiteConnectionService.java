package com.anjunar.blomst.social.sites.site.connections;

import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.blomst.social.sites.SiteConnection;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class SiteConnectionService extends AbstractCriteriaSearchService<SiteConnection, SiteConnectionsSearch> {

    @Inject
    public SiteConnectionService(EntityManager entityManager, IdentityManager identityManager) {
        super(entityManager, identityManager);
    }

    public SiteConnectionService() {
        this(null, null);
    }

}
