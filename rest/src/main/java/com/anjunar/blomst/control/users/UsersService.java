package com.anjunar.blomst.control.users;

import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class UsersService extends AbstractCriteriaSearchService<User, UsersSearch> {

    @Inject
    public UsersService(EntityManager entityManager, IdentityProvider identityProvider) {
        super(entityManager, identityProvider);
    }

    public UsersService() {
        this(null, null);
    }

}
