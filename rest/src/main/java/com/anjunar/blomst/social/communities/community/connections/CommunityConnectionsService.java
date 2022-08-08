package com.anjunar.blomst.social.communities.community.connections;

import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.social.communities.CommunitiesConnection;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class CommunityConnectionsService extends AbstractCriteriaSearchService<CommunitiesConnection, CommunityConnectionsSearch> {

    @Inject
    public CommunityConnectionsService(EntityManager entityManager, IdentityProvider identityProvider) {
        super(entityManager, identityProvider);
    }

    public CommunityConnectionsService() {
        this(null, null);
    }

}
