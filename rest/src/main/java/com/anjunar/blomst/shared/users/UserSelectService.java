package com.anjunar.blomst.shared.users;

import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class UserSelectService extends AbstractCriteriaSearchService<User, UserSelectSearch> {

    @Inject
    public UserSelectService(EntityManager entityManager, IdentityProvider identityProvider) {
        super(entityManager, identityProvider);
    }

    public UserSelectService() {
        this(null, null);
    }

}
