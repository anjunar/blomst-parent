package com.anjunar.blomst.shared.users;

import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class UserSelectService extends AbstractCriteriaSearchService<User, UserSelectSearch> {

    @Inject
    public UserSelectService(EntityManager entityManager, IdentityManager identityProvider) {
        super(entityManager, identityProvider);
    }

    public UserSelectService() {
        this(null, null);
    }

}
