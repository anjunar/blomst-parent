package com.anjunar.blomst.social.timeline.post.likes;

import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class LikesService extends AbstractCriteriaSearchService<User, LikesSearch> {

    @Inject
    public LikesService(EntityManager entityManager, IdentityManager identityManager) {
        super(entityManager, identityManager);
    }

    public LikesService() {
        this(null, null);
    }

}
