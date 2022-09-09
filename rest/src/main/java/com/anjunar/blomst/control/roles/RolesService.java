package com.anjunar.blomst.control.roles;

import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.Role;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

public class RolesService extends AbstractCriteriaSearchService<Role, RolesSearch> {

    @Inject
    public RolesService(EntityManager entityManager, IdentityManager identityManager) {
        super(entityManager, identityManager);
    }

    public RolesService() {
        this(null, null);
    }

}
