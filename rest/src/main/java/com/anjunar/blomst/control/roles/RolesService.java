package com.anjunar.blomst.control.roles;

import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.Role;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

public class RolesService extends AbstractCriteriaSearchService<Role, RolesSearch> {

    @Inject
    public RolesService(EntityManager entityManager, IdentityProvider identityProvider) {
        super(entityManager, identityProvider);
    }

    public RolesService() {
        this(null, null);
    }

}
