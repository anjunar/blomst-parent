package com.anjunar.blomst.social.communities;

import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityProvider;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class CommunitiesService extends AbstractCriteriaSearchService<Community, CommunitiesSearch> {

    @Inject
    public CommunitiesService(EntityManager entityManager, IdentityProvider identityProvider) {
        super(entityManager, identityProvider);
    }

    public CommunitiesService() {
        this(null, null);
    }

}
