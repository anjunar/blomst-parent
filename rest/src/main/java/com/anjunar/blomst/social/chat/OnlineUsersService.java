package com.anjunar.blomst.social.chat;

import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class OnlineUsersService extends AbstractCriteriaSearchService<User, OnlineUsersSearch> {

    @Inject
    public OnlineUsersService(EntityManager entityManager, IdentityManager identityManager) {
        super(entityManager, identityManager);
    }

    public OnlineUsersService() {
        this(null, null);
    }

}
