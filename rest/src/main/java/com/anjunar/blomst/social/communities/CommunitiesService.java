package com.anjunar.blomst.social.communities;

import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

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
