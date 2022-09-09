package com.anjunar.blomst.social.communities;

import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityManager;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class CommunitiesService extends AbstractCriteriaSearchService<Community, CommunitiesSearch> {

    @Inject
    public CommunitiesService(EntityManager entityManager, IdentityManager identityManager) {
        super(entityManager, identityManager);
    }

    public CommunitiesService() {
        this(null, null);
    }

}
