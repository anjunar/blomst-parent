package com.anjunar.blomst.control.users;

import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class UsersService extends AbstractCriteriaSearchService<User, UsersSearch> {

    @Inject
    public UsersService(EntityManager entityManager, IdentityManager identityManager) {
        super(entityManager, identityManager);
    }

    public UsersService() {
        this(null, null);
    }

}
