package com.anjunar.blomst.social.communities.community.connections;

import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.social.communities.CommunitiesConnection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

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
